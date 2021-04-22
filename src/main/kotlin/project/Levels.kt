package xyz.qwewqa.trebla.project

import com.fasterxml.jackson.jr.ob.JSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import xyz.qwewqa.trebla.backend.compile.EngineData
import xyz.qwewqa.trebla.backend.compile.EntityData
import xyz.qwewqa.trebla.cli.PROJECT_CONFIGURATION_FILENAME
import xyz.qwewqa.trebla.frontend.LogErrorListener
import xyz.qwewqa.trebla.frontend.errorMessage
import xyz.qwewqa.trebla.grammar.generated.LevelLexer
import xyz.qwewqa.trebla.grammar.generated.LevelParser
import xyz.qwewqa.trebla.level.IdentifierArgument
import xyz.qwewqa.trebla.level.LevelEntity
import xyz.qwewqa.trebla.level.LevelVisitor
import xyz.qwewqa.trebla.level.NumericalArgument
import java.io.File
import java.util.zip.GZIPOutputStream

val levelExtensions = setOf("tlv", "trlv")

suspend fun generateLevels(path: File) {
    val projectConfig = loadConfig(path.resolve(PROJECT_CONFIGURATION_FILENAME))
    val outDir = path.resolve(projectConfig.out)
    val engineFile = path.resolve(projectConfig.out).resolve(ENGINE_OUT_FILENAME)
    if (!engineFile.exists()) {
        error("Engine not built. Run build first to generate the engine file.")
    }
    @Suppress("UNCHECKED_CAST")
    val engine = withContext(Dispatchers.IO) {
        EngineData.fromMap(JSON.std.mapFrom(engineFile.bufferedReader()) as Map<String, Any>)
    }
    val levelDir = path.resolve(projectConfig.levels)
    if (!levelDir.exists()) levelDir.mkdirs()
    if (!levelDir.isDirectory) error("Level directory is not a directory.")
    val errorChannel = Channel<LevelError>(Channel.UNLIMITED)
    coroutineScope {
        levelDir
            .listFiles()!!
            .filter { it.isFile && it.extension.toLowerCase() in levelExtensions }
            .also { println("Generating ${it.size} level(s).") }
            .forEach {
                launch {
                    val lexer = withContext(Dispatchers.IO) { LevelLexer(CharStreams.fromStream(it.inputStream())) }
                    val tokenStream = CommonTokenStream(lexer)
                    val errorListener = LogErrorListener(it.canonicalPath)
                    val parser = LevelParser(tokenStream).apply {
                        removeErrorListeners()
                        addErrorListener(errorListener)
                    }
                    val visitor = LevelVisitor()
                    val levelFile = parser.levelFile()
                    if (errorListener.errors.isNotEmpty()) {
                        errorListener.errors.forEach { errorChannel.send(LevelSyntaxError(it)) }
                        return@launch
                    }
                    val entities = visitor.visitLevelFile(levelFile)
                    val level = try {
                        engine.compileLevel(compileLevelEntities(entities))
                    } catch (e: IllegalStateException) {
                        errorChannel.send(OtherError(it.canonicalPath, e))
                        return@launch
                    }
                    val levelOutDir = outDir.resolve("levels")
                    if (!levelOutDir.exists()) levelOutDir.mkdir()
                    val levelOutFile = levelOutDir.resolve(it.nameWithoutExtension)
                    withContext(Dispatchers.IO) {
                        GZIPOutputStream(levelOutFile.outputStream()).use {
                            it.write(level.toByteArray())
                        }
                    }
                }
            }
    }
    errorChannel.close()
    val errors = errorChannel.toList()
    if (errors.isNotEmpty()) {
        val syntaxErrors = errors.filterIsInstance<LevelSyntaxError>().map { it.error }
        if (syntaxErrors.isNotEmpty()) {
            println("Syntax errors occurred in at least one level file.")
            println(syntaxErrors.errorMessage())
            println()
        }
        val otherErrors = errors.filterIsInstance<OtherError>()
        if (otherErrors.isNotEmpty()) {
            println("Other errors occurred in at least one level file.")
            otherErrors.sortedBy { it.filename }.forEach {
                println("${it.filename}: ${it.error.message}")
            }
        }
    }
    println("Done.")
}

sealed class LevelError
data class LevelSyntaxError(val error: LogErrorListener.SyntaxError) : LevelError()
data class OtherError(val filename: String, val error: IllegalStateException) : LevelError()

fun compileLevelEntities(entities: List<LevelEntity>): List<EntityData> {
    val symbols = mutableMapOf<String, Double>()
    return entities.mapIndexed { index, levelEntity ->
        if (levelEntity.name != null) {
            symbols[levelEntity.name] = index.toDouble()
        }
        EntityData(levelEntity.archetype, levelEntity.arguments.mapValues { (_, v) ->
            when (v) {
                is NumericalArgument -> v.value
                is IdentifierArgument -> symbols[v.value] ?: error("Unknown symbol '${v.value}'.")
            }
        })
    }
}

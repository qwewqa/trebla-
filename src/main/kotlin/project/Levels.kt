package xyz.qwewqa.sono.project

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import xyz.qwewqa.sono.backend.compile.EngineData
import xyz.qwewqa.sono.backend.compile.EntityData
import xyz.qwewqa.sono.cli.PROJECT_CONFIGURATION_FILENAME
import xyz.qwewqa.sono.frontend.LogErrorListener
import xyz.qwewqa.sono.frontend.SonoCompiler
import xyz.qwewqa.sono.frontend.errorMessage
import xyz.qwewqa.sono.grammar.generated.LevelLexer
import xyz.qwewqa.sono.grammar.generated.LevelParser
import xyz.qwewqa.sono.level.IdentifierArgument
import xyz.qwewqa.sono.level.LevelEntity
import xyz.qwewqa.sono.level.LevelVisitor
import xyz.qwewqa.sono.level.NumericalArgument
import java.io.File

const val LEVEL_FILE_EXTENSION = "slv"

suspend fun generateLevels(path: File) {
    val projectConfig = loadConfig(path.resolve(PROJECT_CONFIGURATION_FILENAME))
    val outDir = path.resolve(projectConfig.out)
    val engineFile = path.resolve(projectConfig.out).resolve(ENGINE_OUT_FILENAME)
    if (!engineFile.exists()) {
        error("Engine not built. Run build first to generate the engine file.")
    }
    val engine = EngineData.fromMap(Gson().fromJson(engineFile.bufferedReader(), Map::class.java) as Map<String, Any>)
    val levelDir = path.resolve(projectConfig.levels)
    if (!levelDir.exists()) levelDir.mkdirs()
    if (!levelDir.isDirectory) error("Level directory is not a directory.")
    val errorChannel = Channel<LevelError>(Channel.UNLIMITED)
    coroutineScope {
        levelDir
            .listFiles()!!
            .filter { it.isFile && it.extension.toLowerCase() == LEVEL_FILE_EXTENSION }
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
                    val (level, options) = try {
                        engine.compileLevel(compileLevelEntities(entities))
                    } catch (e: IllegalStateException) {
                        errorChannel.send(OtherError(it.canonicalPath, e))
                        return@launch
                    }
                    val levelOutDir = outDir.resolve(it.nameWithoutExtension)
                    if (!levelOutDir.exists()) levelOutDir.mkdir()
                    withContext(Dispatchers.IO) {
                        levelOutDir.resolve("level.json").writeText(level)
                        levelOutDir.resolve("options.json").writeText(options)
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
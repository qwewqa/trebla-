package xyz.qwewqa.trebla.project

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.qwewqa.trebla.backend.compile.CompileBackendError
import xyz.qwewqa.trebla.cli.PROJECT_CONFIGURATION_FILENAME
import xyz.qwewqa.trebla.frontend.CompileError
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.TreblaCompiler
import java.io.File
import java.util.zip.GZIPOutputStream

val sourceExtensions = setOf("trb", "treb")
const val ENGINE_OUT_FILENAME = "compiled.json"
const val ENGINE_DATA_OUT_FILENAME = "engine"
const val OPTION_DATA_OUT_FILENAME = "options"

suspend fun build(buildConfig: BuildConfiguration): Boolean {
    try {
        val path = buildConfig.path
        val projectConfig = loadConfig(path.resolve(PROJECT_CONFIGURATION_FILENAME))
        val compiler = TreblaCompiler(CompilerConfiguration(projectConfig, buildConfig))
        val srcDirectory = path.resolve(projectConfig.src)
        val outDirectory = path.resolve(projectConfig.out)
        if (buildConfig.allowSkip) {
            val lastSrcTimestamp = srcDirectory
                .walk()
                .filter { it.isFile && it.extension.lowercase() in sourceExtensions }
                .map { it.lastModified() }
                .maxOrNull() ?: Long.MIN_VALUE
            val engineFile = outDirectory.resolve(ENGINE_OUT_FILENAME)
            if (engineFile.exists() && engineFile.lastModified() >= lastSrcTimestamp) {
                println("Engine already up-to-date. Skipping build.")
                return true
            }
        }
        srcDirectory
            .walk()
            .filter { it.isFile && it.extension.lowercase() in sourceExtensions }
            .toList()
            .also { println("Building ${it.size} source file(s).") }
            .forEach { compiler.loadFile(it) }
        val engine = compiler.process()
        outDirectory.resolve(ENGINE_OUT_FILENAME).writeText(engine.toJson())

        withContext(Dispatchers.IO) {
            GZIPOutputStream(outDirectory.resolve(ENGINE_DATA_OUT_FILENAME).outputStream()).use {
                it.write(engine.toEngineDataJson().toByteArray())
            }
            GZIPOutputStream(outDirectory.resolve(OPTION_DATA_OUT_FILENAME).outputStream()).use {
                it.write(engine.optionsJson().toByteArray())
            }
        }

        println("Build complete. ${engine.nodes.size} node(s), ${engine.scripts.size} script(s), and ${engine.archetypes.size} archetype(s).")
        return true
    } catch (e: Exception) {
        when (e) {
            is CompileError -> {
                var err: CompileError? = e
                while (err != null) {
                    println(err.message)
                    err = err.cause as? CompileError
                    if (err != null) {
                        println()
                        println("Caused By:")
                    }
                }
                return false
            }
            is CompileBackendError -> {
                println("Backend Error: ${e.message}")
                return false
            }
            else -> {
                println("Unexpected Error. This is likely a bug.")
                throw e
            }
        }
    }
}

class BuildConfiguration(
    val path: File,
    val verbose: Boolean,
    val allowSkip: Boolean,
)


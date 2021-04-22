package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.trebla.project.BuildConfiguration
import xyz.qwewqa.trebla.project.loadConfig
import xyz.qwewqa.trebla.project.serve
import java.io.File
import kotlin.system.exitProcess

class ServeCommand : CliktCommand(
    name = "serve",
    help = "Hosts a Sonolus server."
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false).optional()
    override fun run(): Unit = runBlocking {
        val path = path ?: File(".")
        if (!path.resolve(PROJECT_CONFIGURATION_FILENAME).exists()) {
            println("Invalid project directory.")
            exitProcess(-1)
        }
        serve(path)
    }
}

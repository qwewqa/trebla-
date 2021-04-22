package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.trebla.project.BuildConfiguration
import xyz.qwewqa.trebla.project.build
import xyz.qwewqa.trebla.project.generateLevels
import java.io.File

class CompileAllCommand : CliktCommand(
    name = "compileAll",
    help = "Builds engine if outdated, then compiles levels",
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false).optional()
    private val force by option("-f", "--force", help = "Force rebuild").flag()
    override fun run() = runBlocking {
        val path = path ?: File(".").canonicalFile
        if (build(BuildConfiguration(path = path, verbose = false, allowSkip = !force))) {
            generateLevels(path)
        } else {
            println("An error occurred during build.")
        }
    }
}

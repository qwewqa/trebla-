package xyz.qwewqa.sono.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.sono.project.BuildConfiguration
import xyz.qwewqa.sono.project.build
import xyz.qwewqa.sono.project.generateLevels

class CompileAll : CliktCommand(
    name = "compileAll",
    help = "Builds engine if outdated, then compiles levels"
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false)
    override fun run() = runBlocking {
        if (build(BuildConfiguration(path = path, verbose = false, allowSkip = true))) {
            generateLevels(path)
        } else {
            println("An error occurred during build.")
        }
    }
}
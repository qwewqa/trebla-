package xyz.qwewqa.sono.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.sono.project.BuildConfiguration
import xyz.qwewqa.sono.project.build
import xyz.qwewqa.sono.project.generateLevels

const val PROJECT_CONFIGURATION_FILENAME = "sonoproj.yaml"

class Build : CliktCommand(
    "Builds the project in the given directory"
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false)
    private val verbose by option("-v", "--option").flag()
    override fun run(): Unit = runBlocking {
        build(BuildConfiguration(path = path, verbose = verbose, allowSkip = false))
    }
}
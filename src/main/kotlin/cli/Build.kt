package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.trebla.project.BuildConfiguration
import xyz.qwewqa.trebla.project.build

const val PROJECT_CONFIGURATION_FILENAME = "treblaproj.yaml"

class Build : CliktCommand(
    "Builds the project in the given directory"
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false)
    private val verbose by option("-v", "--verbose").flag()
    override fun run(): Unit = runBlocking {
        build(BuildConfiguration(path = path, verbose = verbose, allowSkip = false))
    }
}
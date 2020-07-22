package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.trebla.project.generateLevels

class Levels : CliktCommand(
    "Generates levels based the levels directory"
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false)
    override fun run() = runBlocking {
        generateLevels(path)
    }
}
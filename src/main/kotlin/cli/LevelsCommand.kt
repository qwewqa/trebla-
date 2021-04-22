package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.coroutines.runBlocking
import xyz.qwewqa.trebla.project.generateLevels
import java.io.File

class LevelsCommand : CliktCommand(
    "Generates levels based the levels directory"
) {
    private val path by argument().file(mustExist = true, canBeDir = true, canBeFile = false).optional()
    override fun run() = runBlocking {
        generateLevels(path ?: File(".").canonicalFile)
    }
}

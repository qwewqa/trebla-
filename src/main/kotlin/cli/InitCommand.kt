package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

class InitCommand : CliktCommand(
    name = "init",
    help = "Initializes an empty project in the given directory",
) {
    val path by argument().file(mustExist = false, canBeDir = true, canBeFile = false).optional()
    override fun run() {
        val path = path ?: File(".").canonicalFile
        path.mkdirs()
        if (!path.isDirectory || path.list()!!.isNotEmpty()) {
            System.err.println("Path must be an empty directory")
            return
        }

        val defaultConf = javaClass.getResource("/DefaultProject/DefaultConfiguration.yaml")
        path.resolve(PROJECT_CONFIGURATION_FILENAME).apply {
            createNewFile()
            bufferedWriter().use {
                it.write(defaultConf.readText())
            }
        }

        path.resolve("src").mkdir()
        path.resolve("out").mkdir()
        path.resolve("levels").mkdir()
    }
}

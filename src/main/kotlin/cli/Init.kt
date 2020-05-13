package xyz.qwewqa.sono.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file

class Init : CliktCommand(
    "Initializes an empty project in the given directory"
) {
    val path by argument().file(mustExist = false, canBeDir = true, canBeFile = false)
    override fun run() {
        path.mkdirs()
        if (!path.isDirectory || !path.list()!!.isEmpty()) {
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
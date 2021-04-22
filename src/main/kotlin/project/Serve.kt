package xyz.qwewqa.trebla.project

import spark.kotlin.Http
import spark.kotlin.ignite
import xyz.qwewqa.trebla.cli.PROJECT_CONFIGURATION_FILENAME
import java.io.File

fun serve(projectPath: File) {
    val config = loadConfig(projectPath.resolve(PROJECT_CONFIGURATION_FILENAME))

    val http: Http = ignite()

    http.staticFiles.externalLocation(projectPath.resolve(config.server).canonicalPath)

    http.service.init()

    print("Running on port ${http.port()}...")
}

package xyz.qwewqa.trebla.project

import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings
import xyz.qwewqa.trebla.cli.PROJECT_CONFIGURATION_FILENAME
import java.io.File

@Suppress("UNCHECKED_CAST")
fun loadConfig(file: File): ProjectConfiguration {
    if (!file.isFile) error("$PROJECT_CONFIGURATION_FILENAME does not exist or is not a file.")
    val loadSettings = LoadSettings.builder().build()
    val load = Load(loadSettings)
    val config = file.inputStream().use { load.loadFromInputStream(it) } as Map<String, Any>
    return ProjectConfiguration(
        options = (config["options"] ?: emptyList<Map<String, Any>>()) as List<Map<String, Any>>,
        buckets = (config["buckets"] ?: emptyList<Map<String, Any>>()) as List<Map<String, Any>>,
        src = config["src"] as String,
        out = config["out"] as String,
        levels = config["levels"] as String,
        server = config["server"] as String,
    )
}

data class ProjectConfiguration(
    val options: List<Map<String, Any>>,
    val buckets: List<Map<String, Any>>,
    val src: String,
    val out: String,
    val levels: String,
    val server: String,
)


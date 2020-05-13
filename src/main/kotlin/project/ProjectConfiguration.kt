package xyz.qwewqa.sono.project

import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings
import java.io.File

@Suppress("UNCHECKED_CAST")
fun loadConfig(file: File): ProjectConfiguration {
    val loadSettings = LoadSettings.builder().build()
    val load = Load(loadSettings)
    val config = file.inputStream().use { load.loadFromInputStream(it) } as Map<String, Any>
    return ProjectConfiguration(
        options = (config["options"] ?: emptyList<Map<String, Any>>()) as List<Map<String, Any>>,
        buckets = (config["buckets"] ?: emptyList<Map<String, Any>>()) as List<Map<String, Any>>,
        src = config["src"] as String,
        out = config["out"] as String,
        levels = config["levels"] as String,
    )
}

data class ProjectConfiguration(
    val options: List<Map<String, Any>>,
    val buckets: List<Map<String, Any>>,
    val src: String,
    val out: String,
    val levels: String,
)


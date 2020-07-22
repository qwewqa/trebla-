package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.project.BuildConfiguration
import xyz.qwewqa.trebla.project.ProjectConfiguration

data class CompilerConfiguration(
    val projectConfig: ProjectConfiguration,
    val buildConfig: BuildConfiguration,
)
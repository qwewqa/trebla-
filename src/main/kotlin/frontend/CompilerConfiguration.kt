package xyz.qwewqa.sono.frontend

import xyz.qwewqa.sono.project.BuildConfiguration
import xyz.qwewqa.sono.project.ProjectConfiguration

data class CompilerConfiguration(
    val projectConfig: ProjectConfiguration,
    val buildConfig: BuildConfiguration,
)
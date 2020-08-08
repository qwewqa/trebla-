package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.project.BuildConfiguration
import xyz.qwewqa.trebla.project.ProjectConfiguration
import java.util.concurrent.atomic.AtomicInteger

data class CompilerConfiguration(
    val projectConfig: ProjectConfiguration,
    val buildConfig: BuildConfiguration,
    val sharedState: CompilerSharedState = CompilerSharedState(),
)

class CompilerSharedState {
    val scriptIndex = AtomicInteger(0)
}
package xyz.qwewqa.trebla.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class TreblaCli : CliktCommand(name = "trebla") {
    override fun run() {

    }

    init {
        subcommands(InitCommand(), BuildCommand(), LevelsCommand(), CompileAllCommand())
    }
}

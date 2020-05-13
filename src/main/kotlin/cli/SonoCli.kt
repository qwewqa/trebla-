package xyz.qwewqa.sono.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class SonoCli : CliktCommand(name = "sono") {
    override fun run() {

    }

    init {
        subcommands(Init(), Build(), Levels(), CompileAll())
    }
}
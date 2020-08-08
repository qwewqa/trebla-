package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRNode

/**
 * A statement which directly translates to code.
 */
fun interface Statement {
    fun toIR(): IRNode
}
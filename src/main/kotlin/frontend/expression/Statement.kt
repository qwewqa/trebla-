package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRNode
import xyz.qwewqa.trebla.frontend.Entity

/**
 * A statement which directly translates to code.
 */
interface Statement {
    fun toIR(): IRNode
}
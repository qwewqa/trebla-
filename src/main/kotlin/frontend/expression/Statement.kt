package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.backend.compile.IRNode
import xyz.qwewqa.sono.frontend.Entity

/**
 * A statement which directly translates to code.
 */
interface Statement : Entity {
    fun toIR(): IRNode
}
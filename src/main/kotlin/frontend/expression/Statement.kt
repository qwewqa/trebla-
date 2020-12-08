package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.IRNode
import xyz.qwewqa.trebla.backend.ir.SonoFunction

/**
 * A statement which directly translates to code.
 */
sealed class Statement {
    abstract fun asIR(): IRNode
}

class ValueStatement(val value: IRNode) : Statement() {
    override fun asIR() = value
}

fun IRNode.asStatement() = ValueStatement(this)

class BlockStatement : Statement() {
    private val statements = mutableListOf<Statement>()

    fun add(statement: Statement) {
        statements += statement
    }

    operator fun plusAssign(statement: Statement) = add(statement)

    operator fun plusAssign(irNode: IRNode) = add(ValueStatement(irNode))

    override fun asIR(): IRNode = IRFunctionCall(SonoFunction.Execute, statements.map { it.asIR() })
}

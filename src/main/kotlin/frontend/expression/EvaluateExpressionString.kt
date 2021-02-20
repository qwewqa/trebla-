package xyz.qwewqa.trebla.frontend.expression

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import xyz.qwewqa.trebla.frontend.LogErrorListener
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.SimpleContext
import xyz.qwewqa.trebla.frontend.context.createAndAddChild
import xyz.qwewqa.trebla.grammar.generated.TreblaLexer
import xyz.qwewqa.trebla.grammar.generated.TreblaParser
import xyz.qwewqa.trebla.grammar.trebla.BlockNode
import xyz.qwewqa.trebla.grammar.trebla.TreblaFileVisitor

fun Context.evaluateExpressionInChild(
    allowExecution: Boolean = false,
    operation: ExpressionEvaluatorContext.() -> Unit,
) = if (allowExecution) {
    createAndAddChild()
} else {
    SimpleContext(this)
}.evaluateExpressionDirect(operation)

fun Context.evaluateExpressionDirect(operation: ExpressionEvaluatorContext.() -> Unit) =
    ExpressionEvaluatorContext(this).evaluate(operation)

fun Context.eval(expression: String) = evaluateExpressionInChild(allowExecution = false) { +expression }
fun Context.exec(expression: String) = evaluateExpressionDirect { +expression }

class ExpressionEvaluatorContext(val context: Context) {
    private var lastValue: Value = UnitValue

    fun evaluate(operation: ExpressionEvaluatorContext.() -> Unit): Value {
        operation()
        return lastValue
    }

    infix fun Value.addAs(name: String) {
        context.scope.add(this, name)
    }

    infix fun String.let(value: Value) = value.addAs(this)

    operator fun String.unaryPlus(): Value {
        val lexer = TreblaLexer(CharStreams.fromString("{$this}"))
        val tokenStream = CommonTokenStream(lexer)
        val errorListener = LogErrorListener("internal")
        val parser = TreblaParser(tokenStream).apply {
            removeErrorListeners()
            addErrorListener(errorListener)
        }
        val visitor = TreblaFileVisitor("internal")
        val blockContext = parser.block()
        if (errorListener.errors.isNotEmpty()) {
            compileError("Internal syntax error.")
        }
        val node = visitor.visit(blockContext) as BlockNode
        return (node.value.map { it.parseAndApplyTo(context) }.lastOrNull() ?: UnitValue).also {
            lastValue = it
        }
    }
}

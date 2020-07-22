package xyz.qwewqa.trebla.frontend

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.tree.TerminalNode
import xyz.qwewqa.trebla.grammar.trebla.TreblaNode

/**
 * Represents an error during compilation due to some error in the code being compiled.
 * This does not represent a bug in the compiler itself
 * (unless, of course, it's being thrown when compiling valid code).
 */
class CompileError(message: String, val node: TreblaNode? = null, cause: CompileError? = null) :
    IllegalStateException(message, cause) {
    val filename = node?.filename
    val startLineIndex = when (val ctx = node?.context) {
        is ParserRuleContext -> ctx.start.line
        is TerminalNode -> ctx.symbol.line
        else -> null
    }
    val startCharIndex = when (val ctx = node?.context) {
        is ParserRuleContext -> ctx.start.charPositionInLine
        is TerminalNode -> ctx.symbol.charPositionInLine
        else -> null
    }

    val filenamePrefix = " --> "
    val codePrefix = "  | "
    val startPrefix = " >>>"
    val endPrefix = " <<<"
    val skipIndicator = " ..."
    val fileStartIndicator = " BEG"
    val fileEndIndicator = " END"
    val lookbackLines = 1
    val maxLookaheadLines = 1

    override val message: String?
        get() = if (node != null) "CompileError: ${super.message}\n$filenamePrefix$filename: ($startLineIndex, $startCharIndex):\n${
            node.context.let {
                val startToken = when (it) {
                    is ParserRuleContext -> it.start
                    is TerminalNode -> it.symbol
                    else -> error("You should not be seeing this.")
                }
                val stopToken = when (it) {
                    is ParserRuleContext -> it.stop
                    is TerminalNode -> it.symbol
                    else -> error("You should not be seeing this.")
                }
                val startPosition = startToken.charPositionInLine
                val stopOffset = stopToken.stopIndex - startToken.startIndex
                // ANTLR doesn't seem to let us get things line-by-line, so just get the full text
                // and split it ourselves
                val text = startToken.inputStream
                    .getText(Interval(0, startToken.inputStream.size() - 1))
                    .split("\\r?\\n".toRegex())

                // lines are 1-indexed so we subtract
                val startLine = startToken.line - 1
                val endLine = stopToken.line - 1

                val prefixStartLine = (startLine - lookbackLines).coerceAtLeast(0)
                val bodyEndLine = endLine.coerceAtMost(startLine + maxLookaheadLines)
                val suffixEndLine = (startLine + maxLookaheadLines).coerceAtMost(text.size - 1)

                val location = if (startLine == endLine) {
                    if (stopOffset > 1) {
                        "${" ".repeat(startPosition)}v${"-".repeat(stopOffset - 1)}v"
                    } else {
                        "${" ".repeat(startPosition)}v"
                    }
                } else ""

                val prefix =
                    "${
                        if (startLine > 0) skipIndicator else fileStartIndicator
                    }\n${
                        if (prefixStartLine < startLine)
                            "${text[prefixStartLine until startLine].formatCode()}\n$startPrefix$location\n"
                        else "$startPrefix\n"
                    }"
                val body = text[startLine..bodyEndLine].formatCode()
                val suffix =
                    "${
                        when {
                            suffixEndLine > bodyEndLine -> "\n$endPrefix\n" + text[(bodyEndLine + 1)..suffixEndLine].formatCode()
                            bodyEndLine == endLine -> "\n$endPrefix"
                            else -> ""
                        }
                    }\n${
                        if (suffixEndLine != text.size - 1) skipIndicator else fileEndIndicator
                    }"

                "$prefix$body$suffix"
            }
        }"
        else "CompileError: " + super.message

    private fun List<String>.formatCode() = joinToString("\n").prependIndent(codePrefix)
}

private operator fun <E> List<E>.get(intRange: IntRange) = this.subList(intRange.first, intRange.last + 1)

fun compileError(message: String, node: TreblaNode? = null): Nothing = throw CompileError(message, node)
fun TreblaNode.compileError(message: String): Nothing = compileError(message, this)
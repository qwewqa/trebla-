package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.grammar.trebla.TreblaNode
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Represents some entity which may be represented in code.
 */
interface Entity {
    /**
     * The original node this entity was derived from, if it exists.
     * Useful for identifying the code causing an error.
     */
    val node: TreblaNode? get() = null
}

/**
 * Runs the given [block], returning the result.
 * If a [CompileError] is thrown within the block, rethrows a compile error with the given [message]
 * with the inner error as the cause and with the node of this entity.
 * Otherwise, is equivalent to [run].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> Entity.runWithErrorMessage(message: String, block: () -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    try {
        return block()
    } catch (e: CompileError) {
        throw CompileError(message, node, e)
    }
}

/**
 * Runs the given [block], returning the result.
 * If a [CompileError] is thrown within the block, rethrows a compile error with the given [message]
 * with the inner error as the cause and with this node.
 * Otherwise, is equivalent to [run].
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> TreblaNode.runWithErrorMessage(message: String, block: () -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    try {
        return block()
    } catch (e: CompileError) {
        throw CompileError(message, this, e)
    }
}
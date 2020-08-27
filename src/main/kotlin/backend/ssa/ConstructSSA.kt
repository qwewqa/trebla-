package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.*

const val SSA_RENUMBER_COEF = 1000

fun IRNode.applySSAOptimizations(): IRNode {
    var last: SSANode
    var current = constructSSA()
    do {
        println(current.toIR())
        last = current
        current = current
            .pruneUnusedAssigns()
            .inlineAssigns()
            .propagateCopies()
            .toIR()
            .constructSSA()
    } while (current != last)
    return current.toIR()
}

class SSAContext(
    private val mappings: MutableMap<Int, Int> = mutableMapOf(),
    private val seqMappings: MutableMap<Pair<Int, Int>, Int> = mutableMapOf(),
    private val parent: SSAContext? = null,
) {
    fun child() = SSAContext(mappings.toMutableMap(), seqMappings.toMutableMap(), this)
    fun copy() = SSAContext(mappings.toMutableMap(), seqMappings.toMutableMap(), parent)
    fun split(n: Int) = List(n) { child() }

    fun incrementAndGet(id: Int): Int =
        parent?.incrementAndGet(id)?.also { mappings[id] = it }
            ?: ((mappings[id] ?: (id * SSA_RENUMBER_COEF - 1)) + 1).also {
                mappings[id] = it
                if (it - id * SSA_RENUMBER_COEF >= SSA_RENUMBER_COEF) backendError("Too many temporary uses")
            }

    fun incrementAndGetSeq(id: Int, size: Int): Int =
        parent?.incrementAndGetSeq(id, size)?.also { seqMappings[id to size] = it }
            ?: ((seqMappings[id to size] ?: (id * SSA_RENUMBER_COEF - 1)) + 1).also {
                seqMappings[id to size] = it
                if (it - id * SSA_RENUMBER_COEF >= SSA_RENUMBER_COEF) backendError("Too many temporary uses")
            }

    fun get(id: Int) = mappings[id] ?: backendError("Read before assignment of id $id")
    fun getSeq(id: Int, size: Int) =
        seqMappings[id to size] ?: backendError("Read before seq assignment of id $id and size $size")

    fun applyJoin(branches: List<SSAContext>): List<Phi> {
        return branches.map { it.mappings }.let { allMappings ->
            val keys = allMappings.map { it.keys as Set<Int> }.reduce { a, v -> a + v }
            keys.mapNotNull { id ->
                val phiArgs = allMappings.map { it[id] }
                if (phiArgs.filterNotNull().toSet().size <= 1) {
                    null
                } else {
                    val newId = this.incrementAndGet(id)
                    PhiAssign(newId, phiArgs)
                }
            }
        } + branches.map { it.seqMappings }.let { allMappings ->
            val keys = allMappings.map { it.keys as Set<Pair<Int, Int>> }.reduce { a, v -> a + v }
            keys.mapNotNull { id ->
                val phiArgs = allMappings.map { it[id] }
                if (phiArgs.filterNotNull().toSet().size <= 1) {
                    null
                } else {
                    val newId = this.incrementAndGetSeq(id.first, id.second)
                    PhiSeqAssign(newId, id.second, phiArgs)
                }
            }
        }
    }
}

/*
A few notes:

Apart from control flow functions, function execution order is undefined formally.
Some functions like draw short circuit if their argument is a nonexistent id.
 */

fun IRNode.constructSSA(): SSANode = constructSSA(SSAContext())
private fun IRNode.constructSSA(context: SSAContext): SSANode = when (this) {
    is IRValue -> SSAValue(value)
    is IRTempRead -> SSATempRead(context.get(id))
    is IRSeqTempRead -> SSASeqTempRead(context.getSeq(id, size), size, offset.constructSSA(context))
    is IRTempAssign -> {
        val newRhs = rhs.constructSSA(context)
        val newId = context.incrementAndGet(id)
        SSATempAssign(newId, newRhs)
    }
    is IRSeqTempAssign -> {
        val newRhs = rhs.constructSSA(context)
        val newOffset = offset.constructSSA(context)
        val newId = context.incrementAndGetSeq(id, size)
        SSASeqTempAssign(newId, size, newOffset, newRhs)
    }
    is IRFunctionCall -> when (variant) {
        SonoFunction.If -> conditionAndBranch(context, true)
        SonoFunction.Switch -> conditionAndBranch(context, false)
        SonoFunction.SwitchInteger -> conditionAndBranch(context, false)
        SonoFunction.SwitchWithDefault -> conditionAndBranch(context, true)
        SonoFunction.SwitchIntegerWithDefault -> conditionAndBranch(context, true)
        SonoFunction.And -> conditionAndBranch(context, false)
        SonoFunction.Or -> conditionAndBranch(context, false)
        SonoFunction.While -> {
            val condition = arguments[0].constructSSA(context)
            val baseContext = context.copy()
            val bodyContext = context.child()
            val body = arguments[1].constructSSA(bodyContext)
            val postCondition = arguments[0].constructSSA(bodyContext)
            SSAFunctionCall(
                variant,
                listOf(condition, body, postCondition),
                context.applyJoin(listOf(baseContext, bodyContext)),
            )
        }
        else -> SSAFunctionCall(
            variant,
            arguments.map { it.constructSSA(context) },
            emptyList(),
        )
    }
}

private fun IRFunctionCall.conditionAndBranch(context: SSAContext, complete: Boolean): SSAFunctionCall {
    // functionally like a conditional, so just copy the same logic
    val condition = arguments[0].constructSSA(context)
    val branchContexts = if (complete) { // at least one argument (other than the first) must be executed
        context.split(arguments.size - 1)
    } else {
        context.split(arguments.size - 1) + mutableListOf(context.copy())
    }
    val branches = arguments.drop(1).zip(branchContexts).map { (node, ctx) ->
        node.constructSSA(ctx)
    }
    return SSAFunctionCall(
        variant,
        listOf(condition) + branches,
        context.applyJoin(branchContexts),
    )
}

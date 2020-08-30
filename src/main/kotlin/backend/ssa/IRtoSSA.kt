package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.*

class SSAContext(
    private val mapping: MutableMap<Int, Int> = mutableMapOf(),
    private val parent: SSAContext? = null,
) {
    fun child() = SSAContext(mapping.toMutableMap(), this)
    fun copy() = SSAContext(mapping.toMutableMap(), parent)
    fun split(n: Int) = List(n) { child() }

    fun renumber(location: SingleLocation) = get(location.id)

    fun get(id: Int) =
        SingleLocation(id, mapping[id] ?: backendError("Read before assign of id $id"))

    fun incrementAndRenumber(location: SingleLocation) = incrementAndGet(location.id)

    fun incrementAndGet(id: Int): SingleLocation =
        parent?.incrementAndGet(id)?.also { mapping[it.id] = it.version!! }
            ?: SingleLocation(id, (mapping[id]?.plus(1) ?: 0).also { mapping[id] = it })

    fun incrementAll() =
        mapping.map { (k, v) ->
            mapping[k] = v + 1
            k to v + 1
        }

    fun applyJoin(branches: List<SSAContext>): List<Phi> {
        val mappings = branches.map { it.mapping }
        val keys = mappings.map { it.keys }.reduce(Set<Int>::plus)
        return keys.mapNotNull { id ->
            val phiArgs = mappings.map { it[id] }
            if (phiArgs.filterNotNull().toSet().size <= 1) {
                null
            } else {
                val newId = this.incrementAndRenumber(SingleLocation(id))
                Phi(newId, phiArgs.map { SingleLocation(id, it) })
            }
        }
    }
}

fun IRNode.toSSA(context: SSAContext): SSANode = when (this) {
    is IRValue -> SSAValue(value)
    is IRTempRead -> SSATempRead(context.renumber(location))
    is IRTempAssign -> {
        val newRhs = rhs.toSSA(context)
        val newLocation = context.incrementAndRenumber(location)
        SSATempAssign(newLocation, newRhs)
    }
    is IRSeqTempRead -> SSASeqTempRead(location, offset.toSSA(context))
    is IRSeqTempAssign -> SSASeqTempAssign(location, offset.toSSA(context), rhs.toSSA(context))
    is IRFunctionCall -> when (variant) {
        SonoFunction.If -> conditionAndBranch(context, true)
        SonoFunction.Switch -> conditionAndBranch(context, false)
        SonoFunction.SwitchInteger -> conditionAndBranch(context, false)
        SonoFunction.SwitchWithDefault -> conditionAndBranch(context, true)
        SonoFunction.SwitchIntegerWithDefault -> conditionAndBranch(context, true)
        SonoFunction.And -> shortCircuiting(context)
        SonoFunction.Or -> shortCircuiting(context)
        SonoFunction.While -> {
            val initial = context.incrementAll()
            val baseContext = context.copy()
            val bodyContext = context.child()
            val condition = arguments[0].toSSA(context)
            val body = arguments[1].toSSA(bodyContext)
            val outPhi = context.applyJoin(listOf(baseContext, bodyContext))
            val inPhi = initial.map { (id, version) ->
                Phi(
                    SingleLocation(id, version),
                    listOf(
                        SingleLocation(id, version - 1),
                        // Can set to null just to prevent a phi from referencing itself,
                        // causing it not to be counted as dead even if it never is used elsewhere.
                        // Doesn't really matter otherwise.
                        context.get(id).let { if (it.version == version) null else it },
                    ),
                )
            }
            SSAFunctionCall(
                variant,
                listOf(condition, body),
                inPhi,
                outPhi,
            )
        }
        else -> SSAFunctionCall(
            variant,
            arguments.map { it.toSSA(context) },
            emptyList(),
            emptyList(),
        )
    }
}

/**
 * Used where the first argument, the condition is evaluated once always.
 * If [complete], exactly one branch (other argument) is evaluated, otherwise,
 * exactly one or no branch may be evaluated.
 */
private fun IRFunctionCall.conditionAndBranch(context: SSAContext, complete: Boolean): SSAFunctionCall {
    // functionally like a conditional, so just copy the same logic
    val condition = arguments[0].toSSA(context)
    val branchContexts = if (complete) { // at least one argument (other than the first) must be executed
        context.split(arguments.size - 1)
    } else {
        context.split(arguments.size - 1) + mutableListOf(context.copy())
    }
    val branches = arguments.drop(1).zip(branchContexts).map { (node, ctx) ->
        node.toSSA(ctx)
    }
    return SSAFunctionCall(
        variant,
        listOf(condition) + branches,
        inPhi = emptyList(),
        outPhi = context.applyJoin(branchContexts),
    )
}

private fun IRFunctionCall.shortCircuiting(context: SSAContext): SSAFunctionCall {
    return if (arguments.size > 2) {
        IRFunctionCall(variant, listOf(arguments[0], IRFunctionCall(variant, arguments.drop(1)))).shortCircuiting(
            context)
    } else {
        conditionAndBranch(context, false)
    }
}

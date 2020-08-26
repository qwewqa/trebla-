package xyz.qwewqa.trebla.backend.allocate

import xyz.qwewqa.trebla.backend.compile.*

class SSAContext(
    val mappings: MutableMap<Int, Int> = mutableMapOf(),
    val seqMappings: MutableMap<Pair<Int, Int>, Int> = mutableMapOf(),
) {
    fun copy() = SSAContext(mappings.toMutableMap(), seqMappings.toMutableMap())

    fun incrementAndGet(id: Int) = ((mappings[id] ?: (id * 1000 - 1)) + 1).also { mappings[id] = it }
    fun incrementAndGetSeq(id: Int, size: Int) =
        ((seqMappings[id to size] ?: (id * 1000 - 1)) + 1).also { seqMappings[id to size] = it }

    fun get(id: Int) = mappings[id] ?: backendError("Read before assignment of id $id")
    fun getSeq(id: Int, size: Int) =
        seqMappings[id to size] ?: backendError("Read before seq assignment of id $id and size $size")

    fun applyJoin(branches: List<SSAContext>): List<Phi> {
        return branches.map { it.mappings }.let { allMappings ->
            val keys = allMappings.map { it.keys as Set<Int> }.reduce { a, v -> a + v }
            keys.map { id ->
                val newId = this.incrementAndGet(id)
                PhiAssign(newId, allMappings.map { it[id] })
            }
        } + branches.map { it.seqMappings }.let { allMappings ->
            val keys = allMappings.map { it.keys as Set<Pair<Int, Int>> }.reduce { a, v -> a + v }
            keys.map { id ->
                val newId = this.incrementAndGetSeq(id.first, id.second)
                PhiSeqAssign(newId, id.second, allMappings.map { it[id] })
            }
        }
    }
}

/*
A few notes:

Apart from control flow functions, function execution order is undefined formally.
Some functions like draw short circuit if their argument is a nonexistent id.
 */

fun IRNode.constructSSA(context: SSAContext): SSANode = when (this) {
    is IRValue -> SSAValue(value)
    is IRTempRead -> SSATempRead(context.mappings[id] ?: backendError("Read before assignment of id $id"))
    is IRSeqTempRead -> SSASeqTempRead(context.seqMappings[id to size]
        ?: backendError("Read before assignment of id $id size $size"), size, offset.constructSSA(context))
    is IRTempAssign -> {
        val newId = (context.mappings[id] ?: (id * 1000 - 1)) + 1
        context.mappings[id] = newId
        SSATempAssign(newId, rhs.constructSSA(context))
    }
    is IRSeqTempAssign -> {
        val newId = (context.seqMappings[id to size] ?: (id * 1000 - 1)) + 1
        context.seqMappings[id to size] = newId
        SSASeqTempAssign(newId, size, offset.constructSSA(context), rhs.constructSSA(context))
    }
    is IRFunctionCall -> when (variant) {
        SonoFunction.Execute -> {
            SSAFunctionCall(
                variant,
                arguments.map { it.constructSSA(context) },
                emptyList()
            )
        }
        SonoFunction.If -> {
            val condition = arguments[0].constructSSA(context)
            val branchContexts = mutableListOf<SSAContext>()
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.Switch -> {
            val condition = arguments[0].constructSSA(context)
            // keep original context here too, because it's possible no branch will be executed
            val branchContexts = mutableListOf(context.copy())
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.SwitchInteger -> {
            val condition = arguments[0].constructSSA(context)
            // keep original context here too, because it's possible no branch will be executed
            val branchContexts = mutableListOf(context.copy())
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.SwitchWithDefault -> {
            val condition = arguments[0].constructSSA(context)
            val branchContexts = mutableListOf<SSAContext>()
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.SwitchIntegerWithDefault -> {
            val condition = arguments[0].constructSSA(context)
            val branchContexts = mutableListOf<SSAContext>()
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.And -> {
            // functionally like a conditional, so just copy the same logic
            val condition = arguments[0].constructSSA(context)
            // keep original context here too, because it's possible no branch will be executed
            val branchContexts = mutableListOf(context.copy())
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.Or -> {
            // functionally like a conditional, so just copy the same logic
            val condition = arguments[0].constructSSA(context)
            // keep original context here too, because it's possible no branch will be executed
            val branchContexts = mutableListOf(context.copy())
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        SonoFunction.While -> {
            // functionally like a conditional, so just copy the same logic
            val condition = arguments[0].constructSSA(context)
            // keep original context here too, because it's possible no branch will be executed
            val branchContexts = mutableListOf(context.copy())
            val branches = arguments.drop(1).map { node ->
                node.constructSSA(context).also {
                    branchContexts += context.copy()
                }
            }
            SSAFunctionCall(
                variant,
                listOf(condition) + branches,
                context.applyJoin(branchContexts),
            )
        }
        else -> SSAFunctionCall(
            variant,
            arguments.map { it.constructSSA(context) },
            emptyList(),
        )
    }
}

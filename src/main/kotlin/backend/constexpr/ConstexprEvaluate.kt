package xyz.qwewqa.trebla.backend.constexpr

import xyz.qwewqa.trebla.backend.compile.*
import xyz.qwewqa.trebla.backend.compile.IRFunctionVariant.*
import xyz.qwewqa.trebla.backend.compile.IRFunctionVariant.Set
import kotlin.math.*

fun IRNode.constexprEvaluate(allowTemporary: Boolean = false, allowMemory: Boolean = false): Double =
    constexprEvaluate(ConstexprEvaluationContext(allowTemporary, allowMemory))

fun IRNode.tryConstexprEvaluate(allowTemporary: Boolean = false, allowMemory: Boolean = false): Double? =
    try {
        constexprEvaluate(ConstexprEvaluationContext(allowTemporary, allowMemory))
    } catch (e: ConstexprEvaluationException) {
        null
    }

fun IRNode.constexprEvaluate(context: ConstexprEvaluationContext): Double {
    return when (this) {
        is IRValue -> this.value
        is IRTempRead -> context[this.id]
        is IRTempAssign -> run { context[this.id] = this.rhs.constexprEvaluate(context); 0.0 }
        is IRFunction -> when (variant) {
            Execute -> {
                arguments.dropLast(1).forEach { it.constexprEvaluate(context) }
                arguments.lastOrNull()?.constexprEvaluate(context) ?: 0.0
            }
            If -> {
                if (arguments.size != 3) evalError("If requires exactly 3 arguments.")
                if (arguments[0].constexprEvaluate(context) != 0.0) arguments[1].constexprEvaluate(context)
                else arguments[2].constexprEvaluate(context)
            }
            Switch -> {
                if (arguments.size < 3 || arguments.size % 2 != 1) evalError("Switch requires an odd number of arguments at and at least 3.")
                val cond = arguments[0].constexprEvaluate(context)
                val branches = arguments.drop(1).let {
                    val conditions = it.filterIndexed { index, _ -> index % 2 == 0 }
                    val actions = it.filterIndexed { index, _ -> index % 2 == 0 }
                    conditions.zip(actions)
                }
                for ((condition, action) in branches) {
                    if (condition.constexprEvaluate(context) == cond) {
                        action.constexprEvaluate(context)
                        break
                    }
                }
                0.0
            }
            SwitchWithDefault -> {
                if (arguments.size < 2 || arguments.size % 2 != 0) evalError("SwitchWithDefault requires an even number of arguments at and at least 2.")
                val cond = arguments[0].constexprEvaluate(context)
                val branches = arguments.drop(1).dropLast(1).let {
                    val conditions = it.filterIndexed { index, _ -> index % 2 == 0 }
                    val actions = it.filterIndexed { index, _ -> index % 2 == 0 }
                    conditions.zip(actions)
                }
                for ((condition, action) in branches) {
                    if (condition.constexprEvaluate(context) == cond) {
                        action.constexprEvaluate(context)
                        return 0.0
                    }
                }
                arguments.last().constexprEvaluate(context)
                0.0
            }
            SwitchInteger -> {
                if (arguments.size < 2) evalError("SwitchInteger requires at least 2 arguments.")
                val cond = arguments[0].constexprEvaluate(context)
                if (cond.toInt().toDouble() != cond) return 0.0
                val idx = cond.toInt()
                if (idx >= arguments.size || idx <= 0) return 0.0
                arguments[idx].constexprEvaluate(context)
                0.0
            }
            SwitchIntegerWithDefault -> {
                if (arguments.size < 2) evalError("SwitchIntegerWithDefault requires at least 2 arguments.")
                val cond = arguments[0].constexprEvaluate(context)
                if (cond.toInt().toDouble() != cond) {
                    arguments.last().constexprEvaluate(context)
                    return 0.0
                }
                val idx = cond.toInt()
                if (idx >= arguments.size || idx <= 0) {
                    arguments.last().constexprEvaluate(context)
                    return 0.0
                }
                arguments[idx].constexprEvaluate(context)
                0.0
            }
            While -> {
                if (arguments.size != 2) evalError("While requires exactly 2 arguments.")
                val cond = arguments[0]
                val block = arguments[1]
                while (cond.constexprEvaluate(context) != 0.0) block.constexprEvaluate(context)
                0.0
            }
            Add -> {
                if (arguments.isEmpty()) evalError("Add requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc + d }
            }
            Subtract -> {
                if (arguments.isEmpty()) evalError("Subtract requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc - d }
            }
            Multiply -> {
                if (arguments.isEmpty()) evalError("Multiply requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc * d }
            }
            Divide -> {
                if (arguments.isEmpty()) evalError("Divide requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc / d }
            }
            Mod -> {
                if (arguments.isEmpty()) evalError("Mod requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc % d }
            }
            Power -> {
                if (arguments.isEmpty()) evalError("Power requires at least 1 argument.")
                arguments.map { it.constexprEvaluate(context) }.reduce { acc, d -> acc.pow(d) }
            }
            Log -> {
                if (arguments.size != 1) evalError("Log requires exactly 1 argument.")
                ln(arguments[0].constexprEvaluate(context))
            }
            Equal -> {
                if (arguments.size != 2) evalError("Equal requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) == arguments[1].constexprEvaluate()).toDouble()
            }
            NotEqual -> {
                if (arguments.size != 2) evalError("NotEqual requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) != arguments[1].constexprEvaluate()).toDouble()
            }
            Greater -> {
                if (arguments.size != 2) evalError("Greater requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) > arguments[1].constexprEvaluate()).toDouble()
            }
            GreaterOr -> {
                if (arguments.size != 2) evalError("GreaterOr requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) >= arguments[1].constexprEvaluate()).toDouble()
            }
            Less -> {
                if (arguments.size != 2) evalError("Less requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) < arguments[1].constexprEvaluate()).toDouble()
            }
            LessOr -> {
                if (arguments.size != 2) evalError("LessOr requires exactly 2 arguments.")
                (arguments[0].constexprEvaluate(context) <= arguments[1].constexprEvaluate()).toDouble()
            }
            And -> {
                if (arguments.isEmpty()) evalError("And requires at least 1 argument.")
                // Must short circuit
                arguments.asSequence().map { it.constexprEvaluate(context) }.forEach {
                    if (it == 0.0) return false.toDouble()
                }
                true.toDouble()
            }
            Or -> {
                if (arguments.isEmpty()) evalError("Or requires at least 1 argument.")
                // Must short circuit
                arguments.asSequence().map { it.constexprEvaluate(context) }.forEach {
                    if (it != 0.0) return true.toDouble()
                }
                false.toDouble()
            }
            Not -> {
                if (arguments.size != 1) evalError("Not requires exactly 1 argument.")
                (arguments[0].constexprEvaluate(context) == 0.0).toDouble()
            }
            Min -> {
                if (arguments.size != 1) evalError("Min requires exactly 2 arguments.")
                min(arguments[0].constexprEvaluate(context), arguments[1].constexprEvaluate(context))
            }
            Max -> {
                if (arguments.size != 1) evalError("Max requires exactly 2 arguments.")
                max(arguments[0].constexprEvaluate(context), arguments[1].constexprEvaluate(context))
            }
            Abs -> {
                if (arguments.size != 1) evalError("Abs requires exactly 1 argument.")
                arguments[0].constexprEvaluate(context).absoluteValue
            }
            Sign -> {
                if (arguments.size != 1) evalError("Sign requires exactly 1 argument.")
                arguments[0].constexprEvaluate(context).sign
            }
            Ceil -> {
                if (arguments.size != 1) evalError("Ceil requires exactly 1 argument.")
                ceil(arguments[0].constexprEvaluate(context))
            }
            Floor -> {
                if (arguments.size != 1) evalError("Floor requires exactly 1 argument.")
                floor(arguments[0].constexprEvaluate(context))
            }
            Round -> {
                if (arguments.size != 1) evalError("Round requires exactly 1 argument.")
                arguments[0].constexprEvaluate(context).roundToInt().toDouble() // rounding behavior is toward +inf
            }
            Frac -> {
                if (arguments.size != 1) evalError("Frac requires exactly 1 argument.")
                val value = arguments[0].constexprEvaluate(context)
                value - truncate(value)
            }
            Trunc -> {
                if (arguments.size != 1) evalError("Trunc requires exactly 1 argument.")
                val value = arguments[0].constexprEvaluate(context)
                truncate(value)
            }
            Degree -> {
                if (arguments.size != 1) evalError("Degree requires exactly 1 argument.")
                arguments[0].constexprEvaluate(context) / 2 / Math.PI * 360.0
            }
            Radian -> {
                if (arguments.size != 1) evalError("Radian requires exactly 1 argument.")
                arguments[0].constexprEvaluate(context) * 2 * Math.PI / 360.0
            }
            Sin -> {
                if (arguments.size != 1) evalError("Sin requires exactly 1 argument.")
                sin(arguments[0].constexprEvaluate(context))
            }
            Cos -> {
                if (arguments.size != 1) evalError("Cos requires exactly 1 argument.")
                cos(arguments[0].constexprEvaluate(context))
            }
            Tan -> {
                if (arguments.size != 1) evalError("Tan requires exactly 1 argument.")
                tan(arguments[0].constexprEvaluate(context))
            }
            Sinh -> {
                if (arguments.size != 1) evalError("Sinh requires exactly 1 argument.")
                sinh(arguments[0].constexprEvaluate(context))
            }
            Cosh -> {
                if (arguments.size != 1) evalError("Cosh requires exactly 1 argument.")
                cosh(arguments[0].constexprEvaluate(context))
            }
            Tanh -> {
                if (arguments.size != 1) evalError("Tanh requires exactly 1 argument.")
                tanh(arguments[0].constexprEvaluate(context))
            }
            Arcsin -> {
                if (arguments.size != 1) evalError("Arcsin requires exactly 1 argument.")
                asin(arguments[0].constexprEvaluate(context))
            }
            Arccos -> {
                if (arguments.size != 1) evalError("Arccos requires exactly 1 argument.")
                acos(arguments[0].constexprEvaluate(context))
            }
            Arctan -> {
                if (arguments.size != 1) evalError("Arctan requires exactly 1 argument.")
                atan(arguments[0].constexprEvaluate(context))
            }
            Arctan2 -> {
                if (arguments.size != 2) evalError("Arctan2 requires exactly 2 arguments.")
                atan2(arguments[0].constexprEvaluate(context), arguments[1].constexprEvaluate(context))
            }
//        Clamp -> TODO()
//        Lerp -> TODO()
//        LerpClamped -> TODO()
//        Unlerp -> TODO()
//        UnlerpClamped -> TODO()
//        Remap -> TODO()
//        RemapClamped -> TODO()
//        Smoothstep -> TODO()
//        Random -> TODO()
//        RandomInteger -> TODO()
//        ConstexprRandom -> TODO()
//        ConstexprRandomInteger -> TODO()
            Get -> {
                if (arguments.size != 2) evalError("Get requires exactly 2 arguments.")
                val block = arguments[0].constexprEvaluate(context)
                val index = arguments[1].constexprEvaluate(context)
                if (!block.isInteger() || !index.isInteger()) evalError("Get arguments must be integers.")
                context[block.toInt(), index.toInt()]
            }
            GetShifted -> {
                if (arguments.size != 3) evalError("GetShifted requires exactly 3 arguments.")
                val block = arguments[0].constexprEvaluate(context)
                val index = arguments[1].constexprEvaluate(context)
                val offset = arguments[2].constexprEvaluate(context)
                if (!block.isInteger() || !index.isInteger() || !offset.isInteger()) evalError("GetShifted arguments must be integers.")
                context[block.toInt(), index.toInt() + offset.toInt()]
            }
            Set -> {
                if (arguments.size != 3) evalError("Set requires exactly 3 arguments.")
                val block = arguments[0].constexprEvaluate(context)
                val index = arguments[1].constexprEvaluate(context)
                val value = arguments[2].constexprEvaluate(context)
                if (!block.isInteger() || !index.isInteger()) evalError("Set arguments other than the last must be integers.")
                context[block.toInt(), index.toInt()] = value
                0.0
            }
            SetShifted -> {
                if (arguments.size != 4) evalError("SetShifted requires exactly 4 arguments.")
                val block = arguments[0].constexprEvaluate(context)
                val index = arguments[1].constexprEvaluate(context)
                val offset = arguments[2].constexprEvaluate(context)
                val value = arguments[3].constexprEvaluate(context)
                if (!block.isInteger() || !index.isInteger() || !offset.isInteger()) evalError("SetShifted arguments other than the last must be integers.")
                context[block.toInt(), index.toInt() + offset.toInt()] = value
                0.0
            }
            else -> evalError("Unsupported static eval function $variant.")
        }
    }
}

private fun List<Double>.product() = fold(1.0) { acc, d -> acc * d }
private fun Double.isInteger() = this == this.toInt().toDouble()
private fun Boolean.toDouble() = if (this) 1.0 else 0.0
private fun evalError(message: String): Nothing = throw ConstexprEvaluationException(message)
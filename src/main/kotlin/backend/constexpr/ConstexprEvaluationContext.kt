package xyz.qwewqa.trebla.backend.constexpr

import xyz.qwewqa.trebla.backend.ir.SeqLocation
import xyz.qwewqa.trebla.backend.ir.SingleLocation
import xyz.qwewqa.trebla.backend.ir.TempLocation

class ConstexprEvaluationContext(val allowTemporary: Boolean = false, val allowMemory: Boolean = false) {
    private val memory = mutableMapOf<Pair<Int, Int>, Double>()
    private val temp = mutableMapOf<TempLocation, DoubleArray>()

    operator fun set(block: Int, index: Int, value: Double) {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        memory[Pair(block, index)] = value
    }

    operator fun set(location: SingleLocation, value: Double) {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        temp[location] = doubleArrayOf(value)
    }

    operator fun set(location: SeqLocation, offset: Int, value: Double) {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        if (offset !in 0 until location.size) {
            throw ConstexprEvaluationException("Index out of bounds for sequential temporary access.")
        }
        temp.getOrPut(location) { DoubleArray(location.size) }[offset] = value
    }

    operator fun get(block: Int, index: Int): Double {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        return memory[Pair(block, index)]
            ?: throw ConstexprEvaluationException("Attempted get value from memory that has not been assigned.")
    }

    operator fun get(location: SingleLocation): Double {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        return temp[location]?.get(0)
            ?: throw ConstexprEvaluationException("Attempted get value from temporary that has not been assigned.")
    }

    operator fun get(location: SeqLocation, offset: Int): Double {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        if (offset !in 0 until location.size) {
            throw ConstexprEvaluationException("Index out of bounds for sequential temporary access.")
        }
        return temp.getOrElse(location) {
            throw ConstexprEvaluationException("No sequential temporary with id ${location.id} and size ${location.size} exists.")
        }[offset]
    }
}

class ConstexprEvaluationException(message: String) : IllegalStateException(message)

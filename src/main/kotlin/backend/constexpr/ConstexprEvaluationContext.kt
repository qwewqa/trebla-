package xyz.qwewqa.trebla.backend.constexpr

class ConstexprEvaluationContext(val allowTemporary: Boolean = false, val allowMemory: Boolean = false) {
    val memory = mutableMapOf<Pair<Int, Int>, Double>()
    val temporaries = mutableMapOf<Int, Double>()
    val temporarySeqs = mutableMapOf<Pair<Int, Int>, DoubleArray>()

    operator fun set(block: Int, index: Int, value: Double) {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        memory[Pair(block, index)] = value
    }

    fun setTemp(tempId: Int, value: Double) {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        temporaries[tempId] = value
    }

    fun setSeqTemp(tempId: Int, size: Int, offset: Int, value: Double) {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        if (offset >= size) throw ConstexprEvaluationException("Index out of bounds for sequential temorary access.")
        temporarySeqs.getOrPut(tempId to size) { DoubleArray(size) }[offset] = value
    }

    operator fun get(block: Int, index: Int): Double {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        return memory[Pair(block, index)]
            ?: throw ConstexprEvaluationException("Attempted get value from memory that has not been assigned.")
    }

    fun getTemp(tempId: Int): Double {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        return temporaries[tempId]
            ?: throw ConstexprEvaluationException("Attempted get value from temporary that has not been assigned.")
    }

    fun getSeqTemp(tempId: Int, size: Int, offset: Int): Double {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        if (offset >= size) throw ConstexprEvaluationException("Index out of bounds for sequential temporary access.")
        return temporarySeqs.getOrElse(tempId to size) {
            throw ConstexprEvaluationException("No sequential temporary with id $tempId and size $size exists.")
        }[offset]
    }
}

class ConstexprEvaluationException(message: String) : IllegalStateException(message)

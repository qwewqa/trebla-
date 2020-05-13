package xyz.qwewqa.sono.backend.constexpr

class ConstexprEvaluationContext(val allowTemporary: Boolean = false, val allowMemory: Boolean = false) {
    val memory = mutableMapOf<Pair<Int, Int>, Double>()
    val temporaries = mutableMapOf<Int, Double>()

    operator fun set(block: Int, index: Int, value: Double) {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        memory[Pair(block, index)] = value
    }

    operator fun set(tempId: Int, value: Double) {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        temporaries[tempId] = value
    }

    operator fun get(block: Int, index: Int): Double {
        if (!allowMemory) throw ConstexprEvaluationException("Non temporary values in memory cannot be accessed.")
        return memory[Pair(block, index)]
            ?: throw ConstexprEvaluationException("Attempted get value from memory that has not been assigned.")
    }

    operator fun get(tempId: Int): Double {
        if (!allowTemporary) throw ConstexprEvaluationException("Temporary values in memory cannot be accessed.")
        return temporaries[tempId]
            ?: throw ConstexprEvaluationException("Attempted get value from temporary that has not been assigned.")
    }
}

class ConstexprEvaluationException(message: String) : IllegalStateException(message)

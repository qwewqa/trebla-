package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.*

class EnumDeclaration(
    override val node: EnumDeclarationNode,
    override val parentContext: Context,
) : Declaration,
    Type,
    Allocatable,
    MemberAccessor {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = TypeType

    override val commonName get() = identifier

    override val bindingScope = parentContext.scope

    override val bindingHierarchy: List<List<Type>> = listOf(listOf())

    val variants by lazy {
        val occupied = mutableSetOf<Int>()
        // It's notable that this starts at 1 instead of 0 as might be expected
        // Part of this is because integer switch statements also start with 1
        // Something to make note of is the fact that for initialized values
        // (e.g. as a shared value) the one with ordinal zero will be the default
        var currentOrdinal = 1
        tailrec fun nextOrdinal(): Int {
            return if (currentOrdinal in occupied) {
                currentOrdinal++
                nextOrdinal()
            } else {
                currentOrdinal++
            }
        }
        fun process(ordinal: Int, definition: EnumVariantDefinitionNode) = when (definition) {
            is EnumUnitVariantNode -> EnumUnitVariantData(ordinal, definition, this)
            is EnumStructVariantNode -> EnumStructVariantData(ordinal, definition, this)
            is EnumValueVariantNode -> EnumValueVariantData(ordinal, definition, this)
        }
        val (auto, explicit) = node.variants.partition { it.ordinal == null }
        (explicit.map {
            val ordinal = it.ordinal!!
            if (ordinal in occupied) compileError("Duplicate ordinal", it)
            occupied += ordinal
            process(ordinal, it.definition)
        } + auto.map {
            val ordinal = nextOrdinal()
            process(ordinal, it.definition)
        }).toMutableList().also {
            if (0 !in occupied) {
                it += EnumNoneVariantData(this)
            }
        }
    }

    val dataSize by lazy {
        variants.sumBy { it.size }
    }

    override val allocationSize by lazy {
        dataSize + 1
    }

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        return EnumValue(
            this,
            AllocatedRawValue(allocator.allocate()),
            List(dataSize) { AllocatedRawValue(allocator.allocate()) },
        )
    }

    override fun getMember(name: String, accessingContext: Context?): Value? {
        TODO()
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
        }
    }
}

data class EnumValue(override val type: EnumDeclaration, val ordinal: RawValue, val data: List<RawValue>) : Value,
    Allocated {
    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is EnumValue || other.type != this.type) {
            compileError("Incompatible assignment from ${other.type.commonName} to ${type.commonName}.")
        }
        ordinal.copyFrom(other.ordinal, context)
        data.zip(other.data).forEach { (a, b) -> a.copyFrom(b, context) }
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated {
        return EnumValue(type, ordinal.copyTo(allocator, context), data.map { it.copyTo(allocator, context) })
    }

    override fun toEntityArrayValue(offset: RawValue): Allocated {
        return EnumValue(type, ordinal.toEntityArrayValue(offset), data.map { it.toEntityArrayValue(offset) })
    }
}

sealed class EnumVariantData(val parentEnum: EnumDeclaration) {
    abstract val ordinal: Int

    /**
     * Allocation size excluding the ordinal
     */
    abstract val size: Int

    open val node: TreblaNode? = null
}

class EnumNoneVariantData(parentEnum: EnumDeclaration) : EnumVariantData(parentEnum) {
    override val ordinal = 0
    override val size = 0
}

class EnumUnitVariantData(
    override val ordinal: Int,
    override val node: EnumUnitVariantNode,
    parentEnum: EnumDeclaration,
) :
    EnumVariantData(parentEnum) {
    override val size = 0
}

class EnumValueVariantData(
    override val ordinal: Int,
    override val node: EnumValueVariantNode,
    parentEnum: EnumDeclaration,
) : EnumVariantData(parentEnum) {
    override val size = 0
    val value = parentEnum.parentContext.scope.find(node.identifier.value)
        ?: compileError("Unknown symbol ${node.identifier.value}", node.identifier)
}

/**
 * A struct-like variant. Not really a struct, but looks similar.
 */
class EnumStructVariantData(
    override val ordinal: Int,
    override val node: EnumStructVariantNode,
    parentEnum: EnumDeclaration,
) : EnumVariantData(parentEnum) {
    val fields by lazy {
        node.fields.map { it.parameter }.parse(parentEnum.parentContext)
    }

    override val size by lazy {
        fields.map { it.type }.sumBy {
            (it as? Allocatable)?.allocationSize ?: compileError("Enum structs must have sized fields.", node)
        }
    }
}

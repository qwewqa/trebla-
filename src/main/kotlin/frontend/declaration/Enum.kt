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
    Allocatable {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = TypeType

    override val commonName get() = identifier

    override val bindingScope = parentContext.scope

    override val bindingHierarchy: List<List<Type>> = listOf(listOf(EnumType))

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

    val variantsByOrdinal by lazy {
        variants.associateBy { it.ordinal }
    }

    val variantsByIdentifier by lazy {
        variants.associateBy { it.identifier }
    }

    val dataSize by lazy {
        variants.maxOfOrNull { it.size } ?: 0
    }

    override val allocationSize by lazy {
        dataSize + 1
    }

    override fun allocateOn(allocator: Allocator, context: Context): Allocated = EnumValue(
        this,
        AllocatedRawValue(allocator.allocate()),
        List(dataSize) { AllocatedRawValue(allocator.allocate()) },
    )

    override fun fromFlat(values: List<RawValue>) = EnumValue(
        this,
        values[0],
        values.drop(1)
    )

    override fun getMember(name: String, accessingContext: Context?) =
        variantsByIdentifier[name]?.value

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
        }
    }
}

object EnumType : BuiltinType("Enum")

data class EnumValue(override val type: EnumDeclaration, val ordinal: RawValue, val data: List<RawValue>) : Value,
    Allocated {
    override fun coerceImmutable(): Value? {
        val ordinal = ordinal.coerceImmutable() ?: return null
        val variant = type.variantsByOrdinal[ordinal.value.toInt()]
            ?: compileError("Unexpected ordinal ${ordinal.value}.")
        val dataSize = variant.size
        val data = data.take(dataSize).map { it.coerceImmutable() ?: return null } +
                List(data.size - dataSize) { LiteralRawValue(0.0) }
        return EnumValue(type, ordinal, data)
    }

    /*
    Copies (both to and from) could be optimized by not copying extraneous data values where
    the variant is known at compile time, and this could be implemented in the future.
     */
    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other is EnumUnitVariant) {
            copyFrom(other.value, context)
            return
        }
        if (other !is EnumValue || other.type != this.type) {
            compileError("Incompatible assignment to ${type.commonName} from ${other.type.commonName}.")
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

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "ordinal" -> ordinal.toNumberStruct(type.parentContext)
        else -> null
    }

    override fun flat() = listOf(ordinal) + data
}

sealed class EnumVariant : Value {
    abstract val data: EnumVariantData
}

data class EnumUnitVariant(override val data: EnumVariantData) : EnumVariant(), Allocated {
    override val type = data.parentEnum

    val value = EnumValue(
        data.parentEnum,
        data.ordinal.toLiteralRawValue(),
        List(data.parentEnum.dataSize) { 0.toLiteralRawValue() },
    )

    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Enum variants cannot be mutated.")
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated = value.copyTo(allocator, context)

    override fun toEntityArrayValue(offset: RawValue): Allocated = value.toEntityArrayValue(offset)

    override fun flat(): List<RawValue> = value.flat()

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "ordinal" -> data.ordinal.toLiteralRawValue().toNumberStruct(type.parentContext)
        "parent" -> data.parentEnum
        "value" -> value
        else -> null
    }
}

data class EnumStructVariant(override val data: EnumStructVariantData) : EnumVariant(), Callable {
    override val type = data.parentEnum

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val args = data.fields.pairedWithAndValidated(arguments).byParameterName()
        return EnumValue(type, data.ordinal.toLiteralRawValue(), data.fields.flatMap { (args.getValue(it.name) as Allocated).flat() })
    }

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "ordinal" -> data.ordinal.toLiteralRawValue().toNumberStruct(type.parentContext)
        "parent" -> data.parentEnum
        else -> null
    }
}

sealed class EnumVariantData(val parentEnum: EnumDeclaration) {
    abstract val identifier: String
    abstract val ordinal: Int

    /**
     * Allocation size excluding the ordinal
     */
    abstract val size: Int

    open val node: TreblaNode? = null

    abstract val value: EnumVariant
}

class EnumNoneVariantData(parentEnum: EnumDeclaration) : EnumVariantData(parentEnum) {
    override val identifier = "None"
    override val ordinal = 0
    override val size = 0

    override val value by lazy { EnumUnitVariant(this) }
}

class EnumUnitVariantData(
    override val ordinal: Int,
    override val node: EnumUnitVariantNode,
    parentEnum: EnumDeclaration,
) :
    EnumVariantData(parentEnum) {
    override val identifier = node.identifier.value
    override val size = 0

    override val value by lazy { EnumUnitVariant(this) }
}

/**
 * A struct-like variant. Not really a struct, but looks similar.
 */
class EnumStructVariantData(
    override val ordinal: Int,
    override val node: EnumStructVariantNode,
    parentEnum: EnumDeclaration,
) : EnumVariantData(parentEnum) {
    override val identifier = node.identifier.value

    init {
        if (node.fields.any { it.isEmbed }) {
            compileError("Enum structs do not support embedding.")
        }
    }

    val fields by lazy {
        node.fields.map { it.parameter }.parse(parentEnum.parentContext).apply {
            forEach {
                if (it.type !is Allocatable) compileError("Enum structs require allocatable fields.")
            }
        }
    }

    override val size by lazy {
        fields.map { it.type }.sumBy {
            (it as? Allocatable)?.allocationSize ?: compileError("Enum structs must have sized fields.", node)
        }
    }

    override val value by lazy { EnumStructVariant(this) }
}

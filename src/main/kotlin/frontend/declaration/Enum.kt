package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.CallableDelegate
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.SimpleDeclaration
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.asTreblaAnyList
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

    override val loadEarly = true

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
            is EnumUnitVariantNode -> EnumUnitVariant(ordinal, definition, this)
            is EnumStructVariantNode -> EnumStructVariant(ordinal, definition, this)
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
        })
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

    val hasZeroOrdinal by lazy {
        0 in variantsByOrdinal.keys
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

    override fun getMember(name: String, accessingContext: Context?) = variantsByIdentifier[name]

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

sealed class EnumVariant(val parentEnum: EnumDeclaration) : Value {
    abstract val identifier: String
    abstract val ordinal: Int

    /**
     * Allocation size excluding the ordinal
     */
    abstract val size: Int

    override val node: TreblaNode? = null
}

class EnumUnitVariant(
    override val ordinal: Int,
    override val node: EnumUnitVariantNode,
    parentEnum: EnumDeclaration,
) : EnumVariant(parentEnum), Allocated {
    override val identifier = node.identifier.value
    override val size = 0

    override val type = parentEnum

    val value by lazy {
        EnumValue(
            parentEnum,
            ordinal.toLiteralRawValue(),
            List(parentEnum.dataSize) { 0.toLiteralRawValue() },
        )
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Enum variants cannot be mutated.")
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated = value.copyTo(allocator, context)

    override fun toEntityArrayValue(offset: RawValue): Allocated = value.toEntityArrayValue(offset)

    override fun flat(): List<RawValue> = value.flat()

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "ordinal" -> ordinal.toLiteralRawValue().toNumberStruct(type.parentContext)
        "parent" -> parentEnum
        "value" -> value
        else -> null
    }
}

/**
 * A struct-like variant. Not really a struct, but looks similar.
 */
class EnumStructVariant(
    override val ordinal: Int,
    override val node: EnumStructVariantNode,
    parentEnum: EnumDeclaration,
) : EnumVariant(parentEnum), Callable {
    override val identifier = node.identifier.value

    init {
        node.fields.forEach {
            if (it.isEmbed) {
                compileError("Enum structs do not support embedding.", it)
            }
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
            (it as Allocatable).allocationSize
        }
    }

    fun destructure(value: EnumValue): List<Allocated> {
        val types = fields.map { (it.type as Allocatable) }
        val remaining = ArrayDeque(value.data)
        return types.map { type ->
            type.fromFlat(List(type.allocationSize) { remaining.removeFirst() })
        }
    }

    override val type = CallableType

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val args = fields.pairedWithAndValidated(arguments).byParameterName()
        return EnumValue(parentEnum,
            ordinal.toLiteralRawValue(),
            fields.flatMap { (args.getValue(it.name) as Allocated).flat() })
    }

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "ordinal" -> ordinal.toLiteralRawValue().toNumberStruct(parentEnum.parentContext)
        "parent" -> parentEnum
        else -> null
    }
}

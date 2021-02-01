package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.StructDeclarationNode

class StructDeclaration(
    override val node: StructDeclarationNode,
    override val parentContext: Context,
    val typeArgumentsByName: Map<String, Value> = emptyMap(),
    override val baseType: TypeParamStructDeclaration,
) : Declaration,
    Callable,
    Type,
    Allocatable,
    ParameterizedType {
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility: Visibility
    override val type = TypeType

    override val commonName get() = identifier

    override val bindingScope = parentContext.scope

    override val loadFirstPass = true

    val isRaw: Boolean

    override val typeParameters by lazy {
        baseType.variances.zip(baseType.typeParameterNames).map { (variance, name) ->
            val argument = typeArgumentsByName.getValue(name)
            when (variance) {
                TypeVariance.Equality -> TypeParameter.ValueParameter(argument)
                TypeVariance.Invariant -> TypeParameter.SingleTypeParameter(argument as Type)
                TypeVariance.Covariant -> TypeParameter.SingleTypeParameter(argument as Type)
                TypeVariance.Contravariant -> TypeParameter.SingleTypeParameter(argument as Type)
                TypeVariance.InvariantList -> throw NotImplementedError()
                TypeVariance.CovariantList -> throw NotImplementedError()
                TypeVariance.ContravariantList -> throw NotImplementedError()
            }
        }
    }

    val fields by lazy {
        val paramContext = SimpleContext(parentContext)
        typeArgumentsByName.forEach { (id, v) ->
            paramContext.scope.add(v, id)
        }
        node.fields.map { it.parameter }.parse(parentContext)
    }
    val fieldNames by lazy { fields.map { it.name }.toSet() }
    override val parameters by lazy { fields }

    val embeddedFieldNames = node.fields.filter { it.isEmbed }.map { it.parameter.identifier.value }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Allocated {
        return if (!isRaw) {
            NormalStructValue(fields.pairedWithAndValidated(arguments).byParameterName(), this)
        } else {
            if (arguments.size != 1) compileError("A raw struct constructor should get exactly one argument.")
            val arg = arguments.first()
            if (arg.name != null) compileError("A raw struct constructor does not take a named argument.")
            val value = arg.value
            if (value !is RawStructValue) compileError("A raw struct constructor only takes another raw struct.")
            RawStructValue(value.raw, this)
        }
    }

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        return if (isRaw) RawStructValue(AllocatedRawValue(allocator.allocate()), this)
        else callWith(parameters.map {
            ValueArgument(
                it.name,
                (it.type as? Allocatable)?.allocateOn(allocator, context)
                    ?: compileError("${type.identifier} is not allocatable.")
            )
        }, context)
    }

    override val allocationSize by lazy {
        getSize()
    }

    // lazy delegate won't allow directly using isRaw
    private fun getSize() = if (this.isRaw) 1 else fields.sumBy {
        (it.type as? Allocatable)?.allocationSize ?: compileError("Struct has unsized members.")
    }

    override fun fromFlat(values: List<RawValue>) = if (isRaw) {
        RawStructValue(values[0], this)
    } else {
        var remaining = values
        NormalStructValue(fields.associate {
            val type = (it.type as? Allocatable) ?: compileError("Struct has unsized members.")
            it.name to type
                .fromFlat(remaining.take(type.allocationSize))
                .also { remaining = remaining.drop(type.allocationSize) }
        }, this)
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            isRaw = selectSingle("raw")
        }
    }
}

object StructType : BuiltinType("Struct")

/**
 * The value resulting from a struct construction.
 */
sealed class StructValue(
    override val type: StructDeclaration,
) : Value, Allocated

data class NormalStructValue(
    val fields: Map<String, Value>,
    override val type: StructDeclaration,
) : StructValue(type) {
    override fun coerceImmutable(): NormalStructValue? =
        NormalStructValue(fields.mapValues { (_, v) -> v.coerceImmutable() ?: return null }, type)

    override val embedded = type.embeddedFieldNames.map { fields.getValue(it) }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): StructValue {
        return NormalStructValue(fields.mapValues { (_, value) ->
            when (value) {
                is Allocated -> value.copyTo(allocator, context)
                !is Allocated -> value
                else -> compileError("Invalid copy.")
            }
        }, type)
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is NormalStructValue || type != other.type) compileError("Incompatible assigment.")
        fields.forEach { (name, value) ->
            if (value is Allocated) value.copyFrom(other.fields.getValue(name), context)
            else if (value != other.fields.getValue(name)) compileError("Invalid assigment.")
        }
    }

    override fun getMember(name: String, accessingContext: Context?): Value? =
        fields[name] ?: type.typeArgumentsByName[name]

    override fun toEntityArrayValue(offset: RawValue): Allocated {
        return NormalStructValue(
            fields.mapValues { (_, v) ->
                (v as? Allocated)?.toEntityArrayValue(offset) ?: v
            },
            type,
        )
    }

    override fun flat() = fields.flatMap { (_, v) ->
        (v as? Allocated)?.flat() ?: compileError("Struct contains non allocated members and cannot be flattened.")
    }
}

data class RawStructValue(
    val raw: RawValue,
    override val type: StructDeclaration,
) : StructValue(type) {
    override fun coerceImmutable(): RawStructValue? {
        return RawStructValue(raw.coerceImmutable() ?: return null, type)
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): RawStructValue {
        return RawStructValue(raw.copyTo(allocator, context), type)
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is RawStructValue || type != other.type) compileError("Incompatible assigment.")
        raw.copyFrom(other.raw, context)
    }

    override fun toEntityArrayValue(offset: RawValue): RawStructValue {
        return RawStructValue(
            raw.toEntityArrayValue(offset),
            type
        )
    }

    override fun flat() = listOf(raw)
}

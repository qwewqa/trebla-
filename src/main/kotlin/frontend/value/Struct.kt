package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.*
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

    override val commonName: String
        get() = if (typeParameters.isNotEmpty()) super<ParameterizedType>.commonName else identifier
    override val bindingScope = parentContext.scope

    override val loadFirstPass = true

    override val typeParameters by lazy {
        baseType.variances.zip(baseType.typeParameterNames).map { (variance, name) ->
            val argument = typeArgumentsByName.getValue(name)
            when (variance) {
                TypeVariance.Equality -> argument
                TypeVariance.Invariant -> argument
                TypeVariance.Covariant -> argument
                TypeVariance.Contravariant -> argument
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
    val parameters by lazy { fields }

    val embeddedFieldNames = node.fields.filter { it.isEmbed }.map { it.parameter.identifier.value }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Allocated {
        return StructInstance(fields.pairedWithAndValidated(arguments).byParameterName(), this)
    }

    override fun allocateOn(allocator: Allocator, context: Context): Allocated = callWith(parameters.map {
        ValueArgument(
            it.name,
            (it.type as? Allocatable)?.allocateOn(allocator, context)
                ?: compileError("${type.commonName} is not allocatable.")
        )
    }, context)

    override val allocationSize by lazy {
        fields.sumBy {
            (it.type as? Allocatable)?.allocationSize ?: compileError("Struct has unsized members.")
        }
    }

    override fun fromFlat(values: List<RawValue>): StructInstance {
        var remaining = values
        return StructInstance(fields.associate {
            val type = (it.type as? Allocatable) ?: compileError("Struct has unsized members.")
            it.name to type
                .fromFlat(remaining.take(type.allocationSize))
                .also { remaining = remaining.drop(type.allocationSize) }
        }, this)
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
        }
    }
}

object StructType : BuiltinType("Struct")

data class StructInstance(
    val fields: Map<String, Value>,
    override val type: StructDeclaration,
) : Value, Allocated {
    override fun coerceImmutable(): StructInstance? =
        StructInstance(fields.mapValues { (_, v) -> v.coerceImmutable() ?: return null }, type)

    override val embedded = type.embeddedFieldNames.map { fields.getValue(it) }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): StructInstance =
        StructInstance(fields.mapValues { (_, value) ->
            when (value) {
                is Allocated -> value.copyTo(allocator, context)
                !is Allocated -> value
                else -> compileError("Invalid copy.")
            }
        }, type)

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is StructInstance || type != other.type) compileError("Incompatible assigment.")
        fields.forEach { (name, value) ->
            if (value is Allocated) value.copyFrom(other.fields.getValue(name), context)
            else if (value != other.fields.getValue(name)) compileError("Invalid assigment.")
        }
    }

    override fun getMember(name: String, accessingContext: Context?): Value? =
        fields[name] ?: type.typeArgumentsByName[name]

    override fun toEntityArrayValue(offset: RawValue): Allocated {
        return StructInstance(
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

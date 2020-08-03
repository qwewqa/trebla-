package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.StructDeclarationNode

class StructDeclaration(
    override val node: StructDeclarationNode,
    override val parentContext: Context,
) : Declaration,
    Callable,
    Type,
    MemberAccessor,
    Allocatable {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = TypeType

    override val loadEarly = true

    val isRaw: Boolean

    val fields by lazy { node.fields.value.parse(parentContext) }
    val fieldNames by lazy { fields.map { it.name }.toSet() }
    override val parameters by lazy { fields }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Mutable {
        if (!isRaw) {
            return NormalStructValue(fields.pairedWithAndValidated(arguments).byParameterName(),
                callingContext,
                this)
        } else {
            if (arguments.size != 1) compileError("A raw struct constructor should get exactly one argument.")
            val arg = arguments.first()
            if (arg.name != null) compileError("A raw struct constructor does not take a named argument.")
            val value = arg.value
            if (value !is RawStructValue) compileError("A raw struct constructor only takes another raw struct.")
            return RawStructValue(value.raw, callingContext, this)
        }
    }

    override fun accepts(other: Value): Boolean {
        return other.type == this
    }

    override fun allocateOn(allocator: Allocator, context: Context?): Mutable {
        return if (isRaw) RawStructValue(AllocatedRawValue(allocator.allocate()), context, this)
        else callWith(parameters.map {
            ValueArgument(
                it.name,
                (it.type as? Allocatable)?.allocateOn(allocator, context)
                    ?: compileError("${type.identifier} is not allocatable.")
            )
        }, context)
    }

    /**
     * Using the dot operator on the struct declaration itself returns a value which has a receiver in the accessingContext
     * unbound, or throws an error.
     */
    override fun getMember(name: String, accessingContext: Context?): Value {
        accessingContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        )?.let {
            return it
        }
        compileError("No receiver function with name $name found.")
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return accessingContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        ) != null
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            isRaw = selectSingle("raw")
        }
    }
}

object StructType : BuiltinType("Struct") {
    override fun accepts(other: Value): Boolean = other.type is StructDeclaration
}

/**
 * The value resulting from a struct construction.
 *
 * @property searchContext used for finding methods
 */
sealed class StructValue(
    var searchContext: Context?,
    override val type: StructDeclaration,
) : MemberAccessor, Value, Mutable {
    override fun getMember(name: String, accessingContext: Context?): Value {
        val searchContextValue = searchContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        )
        val accessingContextValue = accessingContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        )
        if (searchContextValue != null && accessingContextValue != null && searchContextValue != accessingContextValue)
            compileError("Ambiguous method name $name.")

        (searchContextValue ?: accessingContextValue)?.let {
            return if (it is FunctionDeclaration) it.boundTo(this) else it
        }
        compileError("Struct has no member or method with name $name.")
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return (searchContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        ) ?: accessingContext?.scope?.find(
            name,
            Signature.TypedReceiver(type)
        )) != null
    }
}

class NormalStructValue(
    val fields: Map<String, Value>,
    searchContext: Context?,
    type: StructDeclaration,
) : StructValue(searchContext, type) {
    override fun copyOn(allocator: Allocator, context: ExecutionContext): StructValue {
        return NormalStructValue(fields.mapValues { (_, value) ->
            when (value) {
                is Mutable -> value.copyOn(allocator, context)
                !is Mutable -> value
                else -> compileError("Invalid copy.")
            }
        }, searchContext, type)
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is NormalStructValue || type != other.type) compileError("Incompatible assigment.")
        fields.forEach { (name, value) ->
            if (value is Mutable) value.copyFrom(other.fields.getValue(name), context)
            else if (value != other.fields.getValue(name)) compileError("Invalid assigment.")
        }
    }

    override fun getMember(name: String, accessingContext: Context?): Value {
        fields[name]?.let { return it }
        return super.getMember(name, accessingContext)
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return fields[name] != null || super.hasMember(name, accessingContext)
    }

    override fun reallocate(allocator: Allocator, context: Context?): Mutable {
        return NormalStructValue((type.allocateOn(allocator, context) as NormalStructValue).fields, searchContext, type)
    }
}

class RawStructValue(
    val raw: RawValue,
    searchContext: Context?,
    type: StructDeclaration,
) : StructValue(searchContext, type) {
    override fun copyOn(allocator: Allocator, context: ExecutionContext): StructValue {
        val newValue = AllocatedRawValue(allocator.allocate())
        context.statements += AllocatedValueAssignment(newValue, raw)
        return RawStructValue(newValue, searchContext, type)
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is RawStructValue || type != other.type) compileError("Incompatible assigment.")
        context.statements += when (raw) {
            is AllocatedRawValue -> AllocatedValueAssignment(raw, other.raw)
            else -> compileError("Value is not mutable.")
        }
    }

    override fun reallocate(allocator: Allocator, context: Context?): Mutable {
        return RawStructValue(AllocatedRawValue(allocator.allocate()), searchContext, type)
    }
}
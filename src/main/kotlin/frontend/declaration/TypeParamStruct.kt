package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.context.visibilityModifiers
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.StructDeclarationNode

class TypeParamStructDeclaration(
    override val node: StructDeclarationNode,
    override val parentContext: Context,
) : Declaration,
    Subscriptable,
    Type {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = TypeType

    override val commonName get() = identifier

    override val parentTypes: List<Type> = listOf(StructType)

    override val loadFirstPass = true

    override val subscriptParameters by lazy {
        node.typeParameters.value.parse(parentContext)
    }

    private val specificTypes = mutableMapOf<Map<String, Value>, StructDeclaration>()

    override fun subscriptWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val typeParams = subscriptParameters.pairedWithAndValidated(arguments).mapValues { (p, v) ->
            v.coerceImmutable() ?: compileError("Struct type parameters must be immutable.", p.node)
        }.byParameterName()
        return specificTypes[typeParams] ?: StructDeclaration(
            node,
            parentContext,
            typeParams,
            this,
        ).also { specificTypes[typeParams] = it }
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            if (selectSingle("raw")) compileError("Type parameters not supported on raw structs")
        }

        val allParameters = node.fields.map { it.parameter } + node.typeParameters.value
        if (allParameters.map { it.identifier.value }.let { it.size != it.toSet().size }) {
            compileError("Duplicate type or value parameter name.", node)
        }
    }
}

package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.DefaultSignature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.context.visibilityModifiers
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.StructDeclarationNode
import xyz.qwewqa.trebla.grammar.trebla.TypeVarianceSpecification

class TypeParamStructDeclaration(
    override val node: StructDeclarationNode,
    override val parentContext: Context,
) : Declaration,
    Subscriptable,
    Type,
    ParameterizableType {
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility: Visibility

    override val commonName get() = identifier

    override val parentTypes: List<Type> = listOf(StructType)

    override val loadFirstPass = true

    val subscriptParameters by lazy {
        node.typeParameters.parse(parentContext)
    }

    val typeParameterNames by lazy {
        subscriptParameters.map { it.name }
    }

    override val variances by lazy {
        node.typeParameters.map { typeParameter ->
            // TODO: make cases for lists; check for validity when annotated types aren't actually types
            when (typeParameter.variance) {
                TypeVarianceSpecification.Default -> TypeVariance.Invariant
                TypeVarianceSpecification.In -> {
                    when {
                        else -> TypeVariance.Contravariant
                    }
                }
                TypeVarianceSpecification.Out -> {
                    when {
                        else -> TypeVariance.Covariant
                    }
                }
            }
        }
    }

    private val parameterizedTypeCache = mutableMapOf<Map<String, Value>, StructDeclaration>()

    override fun subscriptWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val typeParams = subscriptParameters.pairedWithAndValidated(arguments).mapValues { (p, v) ->
            v.coerceImmutable() ?: compileError("Struct type parameters must be immutable.", p.node)
        }.byParameterName()
        return parameterizedTypeCache[typeParams] ?: StructDeclaration(
            node,
            parentContext,
            typeParams,
            this,
        ).also { parameterizedTypeCache[typeParams] = it }
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            if (selectSingle("raw") && node.typeParameters.isNotEmpty()) {
                compileError("Type parameters not supported on raw structs")
            }
        }

        val allParameters = node.fields.map { it.parameter } + node.typeParameters
        if (allParameters.map { it.identifier.value }.let { it.size != it.toSet().size }) {
            compileError("Duplicate type or value parameter name.", node)
        }
    }
}

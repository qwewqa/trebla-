package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.TypeNode

interface Type : Value {
    override val type: Type get() = TypeType
    override val commonName: String get() = "Unnamed Type"
    override fun coerceImmutable() = this
    val parentTypes: List<Type>
    val bindingScope: Scope? get() = null
}

interface ParameterizableType : Type {
    val variances: List<TypeVariance>
}

interface ParameterizedType : Type {
    val baseType: ParameterizableType
    val typeParameters: List<TypeParameter>

    override val parentTypes get() = listOf(baseType)
}

enum class TypeVariance {
    Equality,
    Invariant,
    Covariant,
    Contravariant,
    InvariantList,
    CovariantList,
    ContravariantList,
}

sealed class TypeParameter {
    data class ValueParameter(val value: Value) : TypeParameter()
    data class SingleTypeParameter(val type: Type) : TypeParameter()
    data class TypeListParameter(val types: List<Type>) : TypeParameter()
}

infix fun Type.equivalentTo(other: Type): Boolean =
    if (this is ParameterizedType && other is ParameterizedType) {
        baseType == other.baseType &&
                typeParameters.size == other.typeParameters.size &&
                typeParameters.zip(other.typeParameters).all { (a, b) ->
                    when {
                        a is TypeParameter.SingleTypeParameter && b is TypeParameter.SingleTypeParameter -> a.type equivalentTo b.type
                        a is TypeParameter.TypeListParameter && b is TypeParameter.TypeListParameter -> a.types.zip(b.types)
                            .all { (c, d) -> c equivalentTo d }
                        else -> false
                    }
                }
    } else {
        this == other
    }

infix fun Type.includes(other: Type): Boolean {
    return when (this) {
        is ParameterizedType -> {
            other is ParameterizedType &&
                    ((baseType == other.baseType &&
                            typeParameters.size == other.typeParameters.size &&
                            typeParameters
                                .zip(other.typeParameters)
                                .zip(baseType.variances)
                                .all { (parameter, variance) ->
                                    val (thisParameter, otherParameter) = parameter
                                    when (variance) {
                                        TypeVariance.Equality -> {
                                            val thisParamValue = (thisParameter as TypeParameter.ValueParameter).value
                                            val otherParamValue = (otherParameter as TypeParameter.ValueParameter).value
                                            thisParamValue == otherParamValue
                                        }
                                        TypeVariance.Invariant -> {
                                            val thisParamValue =
                                                (thisParameter as TypeParameter.SingleTypeParameter).type
                                            val otherParamValue =
                                                (otherParameter as TypeParameter.SingleTypeParameter).type
                                            thisParamValue equivalentTo otherParamValue
                                        }
                                        TypeVariance.Covariant -> {
                                            val thisParamValue =
                                                (thisParameter as TypeParameter.SingleTypeParameter).type
                                            val otherParamValue =
                                                (otherParameter as TypeParameter.SingleTypeParameter).type
                                            thisParamValue equivalentTo otherParamValue
                                        }
                                        TypeVariance.Contravariant -> {
                                            val thisParamValue =
                                                (thisParameter as TypeParameter.SingleTypeParameter).type
                                            val otherParamValue =
                                                (otherParameter as TypeParameter.SingleTypeParameter).type
                                            otherParamValue includes thisParamValue
                                        }
                                        TypeVariance.InvariantList -> {
                                            val thisParamValues =
                                                (thisParameter as TypeParameter.TypeListParameter).types
                                            val otherParamValues =
                                                (otherParameter as TypeParameter.TypeListParameter).types
                                            thisParamValues.size == otherParamValues.size && thisParamValues.zip(
                                                otherParamValues).all { (a, b) -> a equivalentTo b }
                                        }
                                        TypeVariance.CovariantList -> {
                                            val thisParamValues =
                                                (thisParameter as TypeParameter.TypeListParameter).types
                                            val otherParamValues =
                                                (otherParameter as TypeParameter.TypeListParameter).types
                                            thisParamValues.size == otherParamValues.size && thisParamValues.zip(
                                                otherParamValues).all { (a, b) -> a includes b }
                                        }
                                        TypeVariance.ContravariantList -> {
                                            val thisParamValues =
                                                (thisParameter as TypeParameter.TypeListParameter).types
                                            val otherParamValues =
                                                (otherParameter as TypeParameter.TypeListParameter).types
                                            thisParamValues.size == otherParamValues.size && thisParamValues.zip(
                                                otherParamValues).all { (a, b) -> b includes a }
                                        }
                                    }
                                }) || other.parentTypes.any { this includes it })
        }
        is UnionType -> if (other is UnionType) other.types.all { it in types } else types.any { it includes other }
        else -> {
            this == other || other.parentTypes.any { this includes it }
        }
    }
}

fun Type.accepts(other: Value) = includes(other.type)

fun Type.matchBest(types: Iterable<Type>) = matchBestIncluded(types.filter { this includes it })

/**
 * Used by [Type.matchBest] but assumes all types are included by [this] and that [types] is not empty.
 */
private fun Type.matchBestIncluded(types: List<Type>): Type = when (this) {
    is UnionType -> {
        types.singleOrNull()
            ?: types.singleOrNull { it in this.types }
            ?: compileError("Ambiguous match for type $commonName.")
    }
    is ParameterizedType -> {
        @Suppress("UNCHECKED_CAST")
        var remaining = types as List<ParameterizedType>
        baseType.variances.forEachIndexed { i, variance ->
            when (variance) {
                TypeVariance.Equality -> {
                } // Nothing to do types should have already been checked to all be subtypes
                TypeVariance.Invariant -> {
                } // Same as Equality
                TypeVariance.Covariant -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.SingleTypeParameter).type
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type }
                    val bestType = ownType.matchBestIncluded(otherTypes)
                    remaining =
                        remaining.filter { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type == bestType }
                }
                TypeVariance.Contravariant -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.SingleTypeParameter).type
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type }
                    val bestType = ownType.matchBestContravariantIncluding(otherTypes)
                    remaining =
                        remaining.filter { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type == bestType }
                }
                TypeVariance.InvariantList -> {
                } // Same as Equality
                TypeVariance.CovariantList -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.TypeListParameter).types
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.TypeListParameter).types }
                    ownType.forEachIndexed { j, ownTypeAtIndex ->
                        val otherTypesAtIndex = otherTypes.map { it[j] }
                        val bestType = ownTypeAtIndex.matchBestIncluded(otherTypesAtIndex)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as TypeParameter.TypeListParameter).types[j] == bestType }
                    }
                }
                TypeVariance.ContravariantList -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.TypeListParameter).types
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.TypeListParameter).types }
                    ownType.forEachIndexed { j, ownTypeAtIndex ->
                        val otherTypesAtIndex = otherTypes.map { it[j] }
                        val bestType = ownTypeAtIndex.matchBestContravariantIncluding(otherTypesAtIndex)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as TypeParameter.TypeListParameter).types[j] == bestType }
                    }
                }
            }
        }
        remaining.singleOrNull() ?: compileError("Ambiguous match for type $commonName.")
    }
    else -> {
        // Either there is only one possibility, there's a type exactly equal to this one,
        // or there's ambiguity (there's no way to know which subtype to choose)
        types.singleOrNull()
            ?: types.firstOrNull() { it == this }
            ?: compileError("Ambiguous match for type $commonName.")
    }
}

private fun Type.matchBestContravariantIncluding(types: List<Type>): Type = when (this) {
    is UnionType -> compileError("Unsupported.")
    is ParameterizedType -> {
        @Suppress("UNCHECKED_CAST")
        var remaining = types as List<ParameterizedType>
        baseType.variances.forEachIndexed { i, variance ->
            when (variance) {
                TypeVariance.Equality -> {
                } // Nothing to do types should have already been checked to all be subtypes
                TypeVariance.Invariant -> {
                } // Same as Equality
                TypeVariance.Contravariant -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.SingleTypeParameter).type
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type }
                    val bestType = ownType.matchBestIncluded(otherTypes)
                    remaining =
                        remaining.filter { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type == bestType }
                }
                TypeVariance.Covariant -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.SingleTypeParameter).type
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type }
                    val bestType = ownType.matchBestContravariantIncluding(otherTypes)
                    remaining =
                        remaining.filter { (it.typeParameters[i] as TypeParameter.SingleTypeParameter).type == bestType }
                }
                TypeVariance.InvariantList -> {
                } // Same as Equality
                TypeVariance.ContravariantList -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.TypeListParameter).types
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.TypeListParameter).types }
                    ownType.forEachIndexed { j, ownTypeAtIndex ->
                        val otherTypesAtIndex = otherTypes.map { it[j] }
                        val bestType = ownTypeAtIndex.matchBestIncluded(otherTypesAtIndex)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as TypeParameter.TypeListParameter).types[j] == bestType }
                    }
                }
                TypeVariance.CovariantList -> {
                    val ownType = (this.typeParameters[i] as TypeParameter.TypeListParameter).types
                    val otherTypes = types.map { (it.typeParameters[i] as TypeParameter.TypeListParameter).types }
                    ownType.forEachIndexed { j, ownTypeAtIndex ->
                        val otherTypesAtIndex = otherTypes.map { it[j] }
                        val bestType = ownTypeAtIndex.matchBestContravariantIncluding(otherTypesAtIndex)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as TypeParameter.TypeListParameter).types[j] == bestType }
                    }
                }
            }
        }
        remaining.singleOrNull() ?: compileError("Ambiguous match for type $commonName.")
    }
    else -> {
        val remaining = types.toMutableSet()
        types.forEach { type ->
            if (type in remaining && remaining.any { type != it && type includes it }) {
                remaining.remove(type)
            }
        }
        remaining.singleOrNull() ?: compileError("Ambiguous match for type $commonName.")
    }
}

abstract class BuiltinType(override val identifier: String) : Type, Declaration {
    override val parentContext: Context? = null
    override val parentTypes: List<Type> = listOf(AnyType)

    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC

    override val commonName get() = identifier
}

abstract class BuiltinParameterizableType(identifier: String, override val variances: List<TypeVariance>) :
    BuiltinType(identifier), ParameterizableType

object ReceiverType :
    BuiltinParameterizableType("Receiver", listOf(TypeVariance.Contravariant, TypeVariance.Contravariant)) {
    operator fun invoke(receiver: Type, value: Type = AnyType) = ParameterizedReceiverType(receiver, value)
}

data class ParameterizedReceiverType(val receiver: Type, val value: Type) : ParameterizedType, Signature {
    override val baseType = ReceiverType
    override val typeParameters =
        listOf(TypeParameter.SingleTypeParameter(receiver), TypeParameter.SingleTypeParameter(value))
}

object AnyType : BuiltinType("Any") {
    override val parentTypes = emptyList<Type>()
}

object TypeType : BuiltinType("Type")

/**
 * A union type, intended as a type constraint for functions where either of
 * a list of types is acceptable.
 */
class UnionType(val types: Set<Type>) : Type {
    override val parentTypes: List<Type> = listOf(AnyType)
    override val commonName = "UnionType(${types.joinToString { it.commonName }})"
}

fun TypeNode.applyIn(context: Context) =
    /*
    The new context ensures there isn't an execution context, which a type shouldn't need.
    TypeNodes are just expressions that return a type,
    but for the purpose of sanity, they should never need an execution context.
    */
    (value.parseAndApplyTo(ReadOnlyContext(context)) as? Type) ?: compileError("Not a valid type.", this)

package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.value.intrinsics.SubscriptableDelegate
import xyz.qwewqa.trebla.frontend.expression.Subscriptable
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.TypeNode

interface Type : Value {
    override val type: ParameterizedTypeType get() = TypeType(this)
    override val commonName: String
    override fun coerceImmutable() = this
    val parentTypes: List<Type>
    val bindingScope: Scope? get() = null
}

/**
 * A supertype of a [ParameterizedType] that can take type parameters.
 */
interface ParameterizableType : Type {
    val variances: List<TypeVariance>
}

/**
 * A subtype of a [ParameterizableType] type that has type parameters.
 */
interface ParameterizedType : Type {
    override val commonName: String
        get() = "${baseType.commonName}[${
            typeParameters
                .zip(baseType.variances)
                .joinToString(", ") { (param, variance) ->
                    when (variance) {
                        TypeVariance.Covariant -> "out "
                        TypeVariance.Contravariant -> "in "
                        TypeVariance.CovariantList -> "out "
                        TypeVariance.ContravariantList -> "in "
                        else -> ""
                    } + ((param as? Value)?.commonName ?: param.toString())
                }
        }]"
    val baseType: ParameterizableType

    /**
     * The type parameters of this type.
     * It's expected that these follow the layout specified in the variances list of the [baseType]
     * and that this is enforced at the creation of this instance.
     */
    val typeParameters: List<Any>

    override val parentTypes: List<Type> get() = listOf(baseType)
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

infix fun Type.equivalentTo(other: Type): Boolean =
    if (this is ParameterizedType && other is ParameterizedType) {
        baseType == other.baseType &&
                typeParameters.size == other.typeParameters.size &&
                typeParameters.zip(other.typeParameters).all { (a, b) ->
                    when {
                        a is Type && b is Type -> a.type equivalentTo b.type
                        a is List<*> && b is List<*> -> a.zip(b)
                            .all { (c, d) -> if (c is Type && d is Type) c equivalentTo d else c == d }
                        else -> a == b
                    }
                }
    } else {
        this == other
    }

@Suppress("UNCHECKED_CAST")
infix fun Type.includes(other: Type): Boolean {
    return other == NothingType || when (this) {
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
                                            thisParameter == otherParameter
                                        }
                                        TypeVariance.Invariant -> {
                                            thisParameter as Type
                                            otherParameter as Type
                                            thisParameter equivalentTo otherParameter
                                        }
                                        TypeVariance.Covariant -> {
                                            thisParameter as Type
                                            otherParameter as Type
                                            thisParameter includes otherParameter
                                        }
                                        TypeVariance.Contravariant -> {
                                            thisParameter as Type
                                            otherParameter as Type
                                            otherParameter includes thisParameter
                                        }
                                        TypeVariance.InvariantList -> {
                                            thisParameter as List<Type>
                                            otherParameter as List<Type>
                                            thisParameter.size == otherParameter.size && thisParameter.zip(
                                                otherParameter).all { (a, b) -> a equivalentTo b }
                                        }
                                        TypeVariance.CovariantList -> {
                                            thisParameter as List<Type>
                                            otherParameter as List<Type>
                                            thisParameter.size == otherParameter.size && thisParameter.zip(
                                                otherParameter).all { (a, b) -> a includes b }
                                        }
                                        TypeVariance.ContravariantList -> {
                                            thisParameter as List<Type>
                                            otherParameter as List<Type>
                                            thisParameter.size == otherParameter.size && thisParameter.zip(
                                                otherParameter).all { (a, b) -> b includes a }
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

fun Type.matchBest(types: Iterable<Type>): MatchTypeResult {
    val candidates = types.filter { this includes it }
    if (candidates.isEmpty()) MatchTypeResult.Failure.NoCandidates
    return matchBestIncluded(candidates.toSet())?.let { MatchTypeResult.Success(it) }
        ?: MatchTypeResult.Failure.Ambiguous
}

sealed class MatchTypeResult {
    class Success(val type: Type) : MatchTypeResult()
    sealed class Failure : MatchTypeResult() {
        object NoCandidates : Failure()
        object Ambiguous : Failure()
    }

    inline fun getOrElse(onFailure: (Failure) -> Type): Type = when (this) {
        is Success -> type
        is Failure -> onFailure(this)
    }
}

/**
 * Used by [Type.matchBest] but assumes all types are included by [this].
 */
@Suppress("UNCHECKED_CAST")
private fun Type.matchBestIncluded(types: Set<Type>): Type? = if (types.isEmpty()) null else when (this) {
    is UnionType -> {
        types.singleOrNull()
            ?: types.singleOrNull { type -> this.types.any { it includes type } }
    }
    is ParameterizedType -> {
        val strictSubtypes = types.filter { it !is ParameterizedType || it.baseType != this.baseType }
        if (strictSubtypes.isNotEmpty()) {
            strictSubtypes.singleOrNull() ?: strictSubtypes.singleOrNull { it != NothingType }
        } else {
            val candidates =
                types.filter { it is ParameterizedType && it.baseType == this.baseType } as List<ParameterizedType>
            var remaining = candidates
            baseType.variances.forEachIndexed { i, variance ->
                when (variance) {
                    TypeVariance.Equality -> {
                    } // Nothing to do types should have already been checked to all be subtypes
                    TypeVariance.Invariant -> {
                    } // Same as Equality
                    TypeVariance.Covariant -> {
                        val ownType = this.typeParameters[i] as Type
                        val otherTypes = candidates.map { it.typeParameters[i] as Type }.toSet()
                        val bestType = ownType.matchBestIncluded(otherTypes)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as Type) == bestType }
                    }
                    TypeVariance.Contravariant -> {
                        val ownType = this.typeParameters[i] as Type
                        val otherTypes = candidates.map { it.typeParameters[i] as Type }.toSet()
                        val bestType = ownType.matchBestContravariantIncluding(otherTypes)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as Type) == bestType }
                    }
                    TypeVariance.InvariantList -> {
                    } // Same as Equality
                    TypeVariance.CovariantList -> {
                        val ownType = this.typeParameters[i] as List<Type>
                        val otherTypes = candidates.map { it.typeParameters[i] as List<Type> }
                        ownType.forEachIndexed { j, ownTypeAtIndex ->
                            val otherTypesAtIndex = otherTypes.map { it[j] }.toSet()
                            val bestType = ownTypeAtIndex.matchBestIncluded(otherTypesAtIndex)
                            remaining =
                                remaining.filter { (it.typeParameters[i] as List<Type>)[j] == bestType }
                        }
                    }
                    TypeVariance.ContravariantList -> {
                        val ownType = this.typeParameters[i] as List<Type>
                        val otherTypes = candidates.map { it.typeParameters[i] as List<Type> }
                        ownType.forEachIndexed { j, ownTypeAtIndex ->
                            val otherTypesAtIndex = otherTypes.map { it[j] }.toSet()
                            val bestType = ownTypeAtIndex.matchBestContravariantIncluding(otherTypesAtIndex)
                            remaining =
                                remaining.filter { (it.typeParameters[i] as List<Type>)[j] == bestType }
                        }
                    }
                }
            }
            remaining.singleOrNull() ?: types.firstOrNull { it == NothingType }
        }
    }
    else -> {
        // Either there is only one possibility, there's a type exactly equal to this one,
        // or there's ambiguity (there's no way to know which subtype to choose)
        // except in the case of Nothing being present, in which case anything else takes precedence over Nothing.
        types.singleOrNull()
            ?: types.singleOrNull { it != NothingType }
            ?: types.singleOrNull { it == this }
    }
}

@Suppress("UNCHECKED_CAST")
private fun Type.matchBestContravariantIncluding(types: Set<Type>): Type? =
    if (types.isEmpty()) null else when (this) {
        is UnionType -> compileError("Unsupported.")
        is ParameterizedType -> {
            val parentCandidate =
                parentTypes.mapNotNull { it.matchBestContravariantIncluding(types) }.toSet().singleOrNull()
            val paramTypes = types.filterIsInstance<ParameterizedType>().filter { it.baseType == this.baseType }
            var remaining = paramTypes
            baseType.variances.forEachIndexed { i, variance ->
                when (variance) {
                    TypeVariance.Equality -> {
                    } // Nothing to do types should have already been checked to all be subtypes
                    TypeVariance.Invariant -> {
                    } // Same as Equality
                    TypeVariance.Contravariant -> {
                        val ownType = this.typeParameters[i] as Type
                        val otherTypes =
                            paramTypes.map { it.typeParameters[i] as Type }.toSet()
                        val bestType = ownType.matchBestIncluded(otherTypes)
                        remaining =
                            remaining.filter { (it.typeParameters[i] as Type) == bestType }
                    }
                    TypeVariance.Covariant -> {
                        val ownType = this.typeParameters[i] as Type
                        val otherTypes =
                            paramTypes.map { it.typeParameters[i] as Type }.toSet()
                        val bestType = ownType.matchBestContravariantIncluding(otherTypes)
                        remaining =
                            remaining.filter { it.typeParameters[i] as Type == bestType }
                    }
                    TypeVariance.InvariantList -> {
                    } // Same as Equality
                    TypeVariance.ContravariantList -> {
                        val ownType = this.typeParameters[i] as List<Type>
                        val otherTypes =
                            paramTypes.map { it.typeParameters[i] as List<Type> }
                        ownType.forEachIndexed { j, ownTypeAtIndex ->
                            val otherTypesAtIndex = otherTypes.map { it[j] }.toSet()
                            val bestType = ownTypeAtIndex.matchBestIncluded(otherTypesAtIndex)
                            remaining =
                                remaining.filter { (it.typeParameters[i] as List<Type>)[j] == bestType }
                        }
                    }
                    TypeVariance.CovariantList -> {
                        val ownType = this.typeParameters[i] as List<Type>
                        val otherTypes =
                            paramTypes.map { it.typeParameters[i] as List<Type> }
                        ownType.forEachIndexed { j, ownTypeAtIndex ->
                            val otherTypesAtIndex = otherTypes.map { it[j] }.toSet()
                            val bestType = ownTypeAtIndex.matchBestContravariantIncluding(otherTypesAtIndex)
                            remaining =
                                remaining.filter { (it.typeParameters[i] as List<Type>)[j] == bestType }
                        }
                    }
                }
            }
            remaining.singleOrNull() ?: parentCandidate

        }
        else -> {
            val remaining = types.toMutableSet()
            types.forEach { type ->
                if (type in remaining && remaining.any { type != it && type includes it }) {
                    remaining.remove(type)
                }
            }
            remaining.singleOrNull()
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
    BuiltinParameterizableType("Receiver", listOf(TypeVariance.Contravariant, TypeVariance.Covariant)) {
    operator fun invoke(receiver: Type, value: Type = AnyType) = ParameterizedReceiverType(receiver, value)
}

data class ParameterizedReceiverType(val receiver: Type, val value: Type) : ParameterizedType, Signature {
    override val baseType = ReceiverType
    override val typeParameters =
        listOf(receiver, value)
}

object AnyType : BuiltinType("Any"), Signature {
    override val parentTypes = emptyList<Type>()
}

object NothingType : BuiltinType("Nothing"), Signature {
    override val parentTypes = emptyList<Type>()
}

object TypeType :
    BuiltinType("Type"),
    Subscriptable by SubscriptableDelegate(
        {
            "supertype" type TypeType
            "subtype" type TypeType
        },
        {
            ParameterizedTypeType("supertype".cast(), "subtype".cast())
        },
    ),
    ParameterizableType {
    override val variances = listOf(TypeVariance.Covariant, TypeVariance.Contravariant)

    operator fun invoke(type: Type) = ParameterizedTypeType(type, type)
}

class ParameterizedTypeType(supertype: Type, subtype: Type) : ParameterizedType {
    override val baseType = TypeType
    override val typeParameters = listOf(supertype, subtype)
}

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

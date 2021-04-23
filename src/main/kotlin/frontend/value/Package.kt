package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.grammar.trebla.TreblaNode

/**
 * Packages are the main method of scoping.
 */
class Package(
    val identifier: List<String>,
    globalContext: GlobalContext,
    val node: TreblaNode? = null,
) : Value {
    val context = globalContext.createChild()
    override val type = PackageType

    override fun getMember(name: String, accessingContext: Context?): Value? =
        context.scope.get(name)

    /**
     * True if the same package or a subpackage
     */
    fun includedBy(other: Package) =
        identifier.size >= other.identifier.size && identifier.zip(other.identifier).all { (a, b) -> a == b }
}

object PackageType : BuiltinType("Package")

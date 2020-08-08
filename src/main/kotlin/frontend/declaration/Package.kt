package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.grammar.trebla.TreblaNode

/**
 * Packages are the main method of scoping.
 */
class Package(
    override val identifier: String,
    override val parentContext: GlobalAllocatorContext,
    override val node: TreblaNode? = null,
) : GlobalAllocatorContext, MemberAccessor, Declaration {
    override val configuration = parentContext.configuration
    override val type = PackageType
    override val bindingContext: Context = parentContext
    override val visibility = Visibility.PUBLIC
    override val signature = Signature.Default
    override val scope = Scope(parentContext.scope)
    override val levelAllocator = parentContext.levelAllocator
    override val tempAllocator = parentContext.tempAllocator

    override fun getMember(name: String, accessingContext: Context?): Value? =
        scope.get(name)
}

object PackageType : BuiltinType("Package")
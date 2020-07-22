package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.grammar.trebla.TreblaNode

/**
 * Packages are the main method of scoping.
 */
class Package(
    override val identifier: String,
    override val declaringContext: GlobalAllocatorContext,
    override val node: TreblaNode? = null,
) : GlobalAllocatorContext, MemberAccessor, Declaration {
    override val type = PackageType
    override val visibility = Visibility.PUBLIC
    override val signature = Signature.Default
    override val scope = Scope(declaringContext.scope)
    override val levelAllocator = declaringContext.levelAllocator
    override val tempAllocator = declaringContext.tempAllocator

    override fun getMember(name: String, accessingContext: Context?) =
        scope.get(name) ?: compileError("No symbol of name $name found")
}

object PackageType : BuiltinType("Package")
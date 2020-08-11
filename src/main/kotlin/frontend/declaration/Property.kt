package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Allocatable
import xyz.qwewqa.trebla.frontend.expression.Allocated
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.PropertyDeclarationNode

/**
 * A property declaration allocates and stores the value of a struct.
 */
class PropertyDeclaration(
    override val node: PropertyDeclarationNode,
    override val parentContext: Context,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = UnitValue

    val variant: PropertyVariant
    val typeConstraint by lazy { node.type?.applyIn(parentContext) }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            variant = selectFromMap(propertyVariants) ?: PropertyVariant.NORMAL
        }
    }

    override fun applyTo(context: Context): Allocated = runWithErrorMessage("Error in property declaration.") {
        val initializer = node.expression?.parse(context)
        val typeConstraint = typeConstraint // loads lazy delegate and allows smart casts
        val allocator = when (variant) {
            PropertyVariant.SPAWN -> {
                if (context !is ScriptContext) compileError("The spawn modifier is only allowed directly within a script.")
                context.memoryAllocator
            }
            PropertyVariant.DATA -> {
                if (context !is ScriptContext) compileError("The data modifier is only allowed directly within a script.")
                if (typeConstraint !is StructDeclaration || !typeConstraint.isRaw) compileError("The data modifier requires an explicit raw type.")
                context.dataAllocator
            }
            PropertyVariant.SHARED -> {
                if (context !is ScriptContext) compileError("The shared modifier is only allowed directly within a script.")
                context.sharedAllocator
            }
            PropertyVariant.LEVEL -> {
                if (context !is GlobalAllocatorContext) compileError("The level modifier is only allowed at the top level.")
                context.levelAllocator
            }
            PropertyVariant.TEMP -> {
                if (context !is GlobalAllocatorContext) compileError("The temp modifier is only allowed at the top level.")
                context.tempAllocator
            }
            PropertyVariant.NORMAL -> {
                if (context !is ExecutionContext)
                    compileError("Property not allowed at this location. The level modifier may be missing.")
                when (context) {
                    is ScriptContext -> context.memoryAllocator
                    else -> context.localAllocator
                }
            }
        }
        val newValue = when (variant) {
            PropertyVariant.NORMAL -> {
                if (initializer == null) {
                    if (allocator !is StandardAllocator) compileError("Temporary property must have an initializer.")
                    if (typeConstraint == null) compileError("Script variable must have either an explicit type or initializer.")
                    if (typeConstraint !is Allocatable)
                        compileError("Property explicit type is invalid. Should typically be a struct.")
                    typeConstraint.allocateOn(allocator, context)
                } else {
                    val rhsValue = initializer.applyTo(context)
                    if (rhsValue !is Allocated)
                        compileError("Invalid initializer. Should be a struct.")
                    rhsValue.copyTo(allocator, context as ExecutionContext)
                }
            }
            else -> {
                if (initializer != null)
                    compileError("Property with modifier ${
                        variant.toString().toLowerCase()
                    } does not take an initializer.")
                if (typeConstraint == null)
                    compileError("Property with modifier ${
                        variant.toString().toLowerCase()
                    } must have an explicit type.")
                if (typeConstraint !is Allocatable)
                    compileError("Property explicit type is invalid. Should typically be a struct.")
                typeConstraint.allocateOn(allocator, context)
            }
        }
        if (typeConstraint != null && !typeConstraint.accepts(newValue))
            compileError("Expression type does not match specified type.")
        context.scope.add(newValue, identifier, signature, visibility)
        newValue
    }

}

enum class PropertyVariant(val initialized: Boolean = false) {
    /**
     * Located in entity memory either as a member property of the script,
     * or as a temporary variable.
     * Takes an initializer.
     * Declared directly in a script, within a callback, or in a function as a temporary variable.
     */
    NORMAL(true),

    /**
     * Located in entity memory. Does not take an initializer.
     * May be initialized via spawning.
     * Declared directly in a script.
     */
    SPAWN,

    /**
     * Located in entity data. Immutable.
     * Initialized through the level in archetype or entity data.
     * Declared directly in a script.
     * Must have a raw type.
     */
    DATA,

    /**
     * Located in shared entity data.
     * Does not take an initializer.
     * Declared directly in a script.
     */
    SHARED,

    /**
     * Located in level data.
     * Does not take an initializer.
     * Declared at the top level outside of scripts.
     */
    LEVEL,


    /**
     * Located in temporary memory.
     * Does not take an initializer.
     * Declared at the top level outside of scripts.
     */
    TEMP
}

val propertyVariants = mapOf(
    "spawn" to PropertyVariant.SPAWN,
    "data" to PropertyVariant.DATA,
    "shared" to PropertyVariant.SHARED,
    "level" to PropertyVariant.LEVEL,
    "temp" to PropertyVariant.TEMP
)
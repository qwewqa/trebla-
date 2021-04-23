package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Allocatable
import xyz.qwewqa.trebla.frontend.expression.Allocated
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
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
    override val signature = DefaultSignature
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

    override fun applyTo(context: Context): Value = node.runWithErrorMessage("Error in property declaration.") {
        if (identifier.all { it == '_' }) {
            compileError("All underscore identifiers are reserved.")
        }
        val initializer = node.expression?.parse(context)
        val typeConstraint = typeConstraint // loads lazy delegate and allows smart casts
        val allocator = when (variant) {
            PropertyVariant.SPAWN -> {
                if (context !is ScriptContext) compileError("The spawn modifier is only allowed directly within a script.")
                context.memoryAllocator
            }
            PropertyVariant.DATA -> {
                context.dataAllocator ?: compileError("Invalid location for allocation type.")
            }
            PropertyVariant.SHARED -> {
                context.sharedAllocator ?: compileError("Invalid location for allocation type.")
            }
            PropertyVariant.LEVEL -> {
                context.levelAllocator ?: compileError("Invalid location for allocation type.")
            }
            PropertyVariant.LEVELDATA -> {
                context.leveldataAllocator ?: compileError("Invalid location for allocation type.")
            }
            PropertyVariant.TEMP -> {
                context.tempAllocator ?: compileError("Invalid location for allocation type.")
            }
            PropertyVariant.NORMAL -> {
                when (context) {
                    is ScriptContext -> context.memoryAllocator
                    is ExecutionContext -> context.localAllocator
                    else -> compileError("Normal property not allowed at this location. The level modifier may be missing.")
                }
            }
        }
        val newValue = when (variant) {
            PropertyVariant.NORMAL -> {
                if (initializer == null) {
                    if (allocator !is StandardAllocator) compileError("Temporary property must have an initializer.")
                    if (typeConstraint == null) compileError("Script variable must have either an explicit type or initializer.")
                    if (typeConstraint !is Allocatable)
                        compileError("Type is not allocatable.")
                    typeConstraint.allocateOn(allocator, context)
                } else {
                    if (context !is ExecutionContext) compileError("Initializer not allowed in non-execution context.")
                    val rhsValue = initializer.applyTo(context)
                    if (rhsValue !is Allocated)
                        compileError("Invalid initializer. Should be a struct.")
                    rhsValue.copyTo(allocator, context)
                }
            }
            else -> {
                if (initializer != null)
                    compileError("Property with modifier ${
                        variant.toString().lowercase()
                    } does not take an initializer.")
                if (typeConstraint == null)
                    compileError("Property with modifier ${
                        variant.toString().lowercase()
                    } must have an explicit type.")
                if (typeConstraint !is Allocatable)
                    compileError("Property explicit type is invalid. Should typically be a struct.")
                typeConstraint.allocateOn(allocator, context)
            }
        }
        if (typeConstraint != null && !typeConstraint.accepts(newValue))
            compileError("Value of type ${newValue.type.commonName} does not match specified type ${typeConstraint.commonName}.")
        context.scope.add(newValue, identifier, signature, visibility)
        return newValue
    }

}

enum class PropertyVariant {
    /**
     * Located in entity memory either as a member property of the script,
     * or as a temporary variable.
     * Takes an initializer.
     * Declared directly in a script, within a callback, or in a function as a temporary variable.
     */
    NORMAL,

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
     * Located in level memory.
     * Does not take an initializer.
     * Declared at the top level outside of scripts.
     */
    LEVEL,

    /**
     * Located in level data.
     * Does not take an initializer.
     * Declared at the top level outside of scripts.
     * Only modifiable in preprocessing.
     */
    LEVELDATA,


    /**
     * Located in temporary memory.
     * Does not take an initializer.
     * Declared at the top level outside of scripts.
     */
    TEMP
}

private val propertyVariants = mapOf(
    "spawn" to PropertyVariant.SPAWN,
    "data" to PropertyVariant.DATA,
    "shared" to PropertyVariant.SHARED,
    "level" to PropertyVariant.LEVEL,
    "leveldata" to PropertyVariant.LEVELDATA,
    "temp" to PropertyVariant.TEMP
)

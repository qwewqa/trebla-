package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.backend.compile.Archetype
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ReadOnlyContext
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.ArchetypeDeclarationNode

class ArchetypeDeclaration(override val node: ArchetypeDeclarationNode, override val parentContext: Context) :
    Declaration {
    override val identifier = node.name.value
    override val signature = ArchetypeType
    override val visibility = Visibility.PUBLIC
    override val type = AnyType

    val isInput = node.isInput

    fun process() = node.runWithErrorMessage("Error in archetype declaration.") {
        val script = (parentContext.scope.find(node.script.value) as? ScriptDeclaration)
            ?: compileError("Invalid script.")
        val defaults = node.defaults.value.associate {
            it.identifier.value to it.value.parseAndApplyTo(ReadOnlyContext(parentContext)).let { value ->
                if (value.type != script.dataProperties[it.identifier.value]?.type)
                    compileError("Mismatched type on ${it.identifier.value}", it)
                (value as? PrimitiveInstance)?.value?.toIR(null)
                    ?.tryConstexprEvaluate()
                    ?: compileError("Invalid archetype default. Must have a constant numerical or boolean value.")
            }
        }
        Archetype(identifier, script.index, isInput, defaults)
    }
}

object ArchetypeType : BuiltinType("Archetype"), Signature

data class ArchetypeData(val name: String, val script: ScriptDeclaration, val defaults: Map<String, Double>)

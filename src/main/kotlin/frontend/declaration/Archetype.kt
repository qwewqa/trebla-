package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.backend.compile.Archetype
import xyz.qwewqa.sono.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.ReadOnlyContext
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.expression.parseAndApplyTo
import xyz.qwewqa.sono.frontend.runWithErrorMessage
import xyz.qwewqa.sono.grammar.sono.ArchetypeDeclarationNode

class ArchetypeDeclaration(override val node: ArchetypeDeclarationNode, override val declaringContext: Context) :
    Declaration {
    override val identifier = node.name.value
    override val signature = Signature.Archetype
    override val visibility = Visibility.PUBLIC
    override val type = AnyType

    val isInput = node.isInput

    fun finalize() = runWithErrorMessage("Error in archetype declaration.") {
        val script = (declaringContext.scope.find(node.script.value) as? ScriptDeclaration)
            ?: compileError("Invalid script.")
        val defaults = node.defaults.value.associate {
            it.identifier.value to it.value.parseAndApplyTo(ReadOnlyContext(declaringContext)).let { value ->
                if (value.type != script.dataProperties[it.identifier.value]?.type)
                    compileError("Mismatched type on ${it.identifier.value}", it)
                (value as? RawStructValue)?.value?.toIR()
                    ?.tryConstexprEvaluate()
                    ?: compileError("Invalid archetype default. Must have a constant numerical or boolean value.")
            }
        }
        Archetype(identifier, script.index, isInput, defaults)
    }
}

data class ArchetypeData(val name: String, val script: ScriptDeclaration, val defaults: Map<String, Double>)
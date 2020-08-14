package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.declaration.Declaration
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.expression.*

open class SimpleDeclaration(
    override val parentContext: Context?,
    override val identifier: String,
    override val type: Type,
    override val signature: Signature = Signature.Default,
    override val visibility: Visibility = Visibility.PUBLIC,
) : Declaration

class CallableDSL(
    val parentContext: Context,
    parameters: IntrinsicParameterDSLContext.() -> Unit,
    private val operation: IntrinsicFunctionDSLContext.() -> Value,
) : Callable {

    override val parameters by lazy {
        IntrinsicParameterDSLContext(parentContext).apply(parameters).get()?.map { p ->
            Parameter(p.name,
                p.type,
                p.default?.let { DefaultParameter(ValueExpression(it), parentContext) })
        }
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value =
        parameters?.let {
            IntrinsicFunctionDSLContext(it.pairedWithAndValidated(arguments), null, callingContext).operation()
        } ?: IntrinsicFunctionDSLContext(null, arguments, callingContext).operation()
}

class SubscriptableDSL(
    val parentContext: Context,
    parameters: IntrinsicParameterDSLContext.() -> Unit,
    private val operation: IntrinsicFunctionDSLContext.() -> Value,
) : Subscriptable {

    override val subscriptParameters by lazy {
        IntrinsicParameterDSLContext(parentContext).apply(parameters).get()?.map { p ->
            Parameter(p.name,
                p.type,
                p.default?.let { DefaultParameter(ValueExpression(it), parentContext) })
        }
    }

    override fun subscriptWith(arguments: List<ValueArgument>, callingContext: Context): Value =
        subscriptParameters?.let {
            IntrinsicFunctionDSLContext(it.pairedWithAndValidated(arguments), null, callingContext).operation()
        } ?: IntrinsicFunctionDSLContext(null, arguments, callingContext).operation()
}

data class IntrinsicParameter(val name: String, val type: Type, val default: Value?)
class IntrinsicParameterDSLContext(val context: Context) {
    private val params = mutableListOf<IntrinsicParameter>()

    private var managed = true

    val unmanaged: Unit
        get() {
            managed = false
        }

    infix fun String.type(type: Type) =
        IntrinsicParameter(this, type, null).also { params += it }

    infix fun String.type(type: IntrinsicType) =
        IntrinsicParameter(
            this,
            context.run(type.accessor),
            null,
        ).also { params += it }

    infix fun IntrinsicParameter.default(value: Value) =
        copy(default = value).also { new ->
            params.removeLast().let { if (it != this) error("Out of order default use in parameter DSL") }
            params += new
        }

    val NumberType = TreblaNumberType
    val BooleanType = TreblaBooleanType

    fun get() = if (managed) params else {
        if (params.isNotEmpty()) compileError("Unmanaged parameters requires none to be specified in DSL.")
        null
    }
}

sealed class IntrinsicType(val accessor: Context.() -> Type)
object TreblaNumberType : IntrinsicType(Context::numberType)
object TreblaBooleanType : IntrinsicType(Context::booleanType)

class IntrinsicFunctionDSLContext(
    private val _parameters: Map<Parameter, Value>?,
    private val _arguments: List<ValueArgument>?,
    val callingContext: Context,
) {
    val String.number
        get() = cast<RawStructValue>().raw.toIR().tryConstexprEvaluate()
            ?: compileError("Argument not a compile time constant.", parameters[this]?.second?.node)

    val String.boolean
        get() = (cast<RawStructValue>().raw.toIR().tryConstexprEvaluate()
            ?: compileError("Argument not a compile time constant.", parameters[this]?.second?.node)) != 0.0

    inline fun <reified T : Any> String.cast(): T =
        (parameters[this] ?: error("Unknown parameter $this.")).let { (param, value) ->
            (value as? T) ?: compileError("Failed to cast parameter with type ${param.type.repr()}", value.node)
        }

    val arguments get() = _arguments ?: error("Parameters are managed; direct argument access is invalid.")
    val parameters by lazy {
        _parameters?.toList()?.associateBy { it.first.name }
            ?: error("Parameters are unmanaged; use the argument list instead.")
    }
}

fun Number.toStruct(context: Context) = RawStructValue(this.toLiteralRawValue(), context, context.numberType)
fun Boolean.toStruct(context: Context) = RawStructValue(this.toLiteralRawValue(), context, context.booleanType)

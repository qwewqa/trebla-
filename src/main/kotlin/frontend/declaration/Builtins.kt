package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.ExecutionContext
import xyz.qwewqa.sono.frontend.context.MemberAccessor
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.expression.*

/**
 * A special entity which provides access to builtin functions (Sonolus node function).
 * A member access yields [BuiltinFunction] callable which may be called with an arbitrary number of arguments,
 * which must be of a raw type.
 */
object Builtins : MemberAccessor, Declaration {
    override val declaringContext: Context? = null
    override val type = AnyType

    override val identifier = "builtins"
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override fun getMember(name: String, accessingContext: Context?) = try {
        BuiltinFunction(BuiltinFunctionVariant.valueOf(name))
    } catch (e: IllegalArgumentException) {
        compileError("Unknown builtin function $name.")
    }
}

/**
 * All supported types of builtin functions.
 *
 */
enum class BuiltinFunctionVariant(val returns: Boolean = true, val ir: FunctionIRNodeVariant) {
    Execute(false, FunctionIRNodeVariant.Execute),
    ExecuteExpr(true, FunctionIRNodeVariant.Execute),
    If(false, FunctionIRNodeVariant.If),
    IfExpr(true, FunctionIRNodeVariant.If),
    Switch(false, FunctionIRNodeVariant.Switch),
    SwitchWithDefault(false, FunctionIRNodeVariant.SwitchWithDefault),
    SwitchInteger(false, FunctionIRNodeVariant.SwitchInteger),
    SwitchIntegerWithDefault(false, FunctionIRNodeVariant.SwitchIntegerWithDefault),
    While(false, FunctionIRNodeVariant.While),
    Add(ir = FunctionIRNodeVariant.Add),
    Subtract(ir = FunctionIRNodeVariant.Subtract),
    Multiply(ir = FunctionIRNodeVariant.Multiply),
    Divide(ir = FunctionIRNodeVariant.Divide),
    Mod(ir = FunctionIRNodeVariant.Mod),
    Power(ir = FunctionIRNodeVariant.Power),
    Log(ir = FunctionIRNodeVariant.Log),
    Equal(ir = FunctionIRNodeVariant.Equal),
    NotEqual(ir = FunctionIRNodeVariant.NotEqual),
    Greater(ir = FunctionIRNodeVariant.Greater),
    GreaterOr(ir = FunctionIRNodeVariant.GreaterOr),
    Less(ir = FunctionIRNodeVariant.Less),
    LessOr(ir = FunctionIRNodeVariant.LessOr),
    And(ir = FunctionIRNodeVariant.And),
    Or(ir = FunctionIRNodeVariant.Or),
    Not(ir = FunctionIRNodeVariant.Not),
    Min(ir = FunctionIRNodeVariant.Min),
    Max(ir = FunctionIRNodeVariant.Max),
    Abs(ir = FunctionIRNodeVariant.Abs),
    Sign(ir = FunctionIRNodeVariant.Sign),
    Ceil(ir = FunctionIRNodeVariant.Ceil),
    Floor(ir = FunctionIRNodeVariant.Floor),
    Round(ir = FunctionIRNodeVariant.Round),
    Frac(ir = FunctionIRNodeVariant.Frac),
    Trunc(ir = FunctionIRNodeVariant.Trunc),
    Degree(ir = FunctionIRNodeVariant.Degree),
    Radian(ir = FunctionIRNodeVariant.Radian),
    Sin(ir = FunctionIRNodeVariant.Sin),
    Cos(ir = FunctionIRNodeVariant.Cos),
    Tan(ir = FunctionIRNodeVariant.Tan),
    Sinh(ir = FunctionIRNodeVariant.Sinh),
    Cosh(ir = FunctionIRNodeVariant.Cosh),
    Tanh(ir = FunctionIRNodeVariant.Tanh),
    Arcsin(ir = FunctionIRNodeVariant.Arcsin),
    Arccos(ir = FunctionIRNodeVariant.Arccos),
    Arctan(ir = FunctionIRNodeVariant.Arctan),
    Arctan2(ir = FunctionIRNodeVariant.Arctan2),
    Clamp(ir = FunctionIRNodeVariant.Clamp),
    Lerp(ir = FunctionIRNodeVariant.Lerp),
    LerpClamped(ir = FunctionIRNodeVariant.LerpClamped),
    Unlerp(ir = FunctionIRNodeVariant.Unlerp),
    UnlerpClamped(ir = FunctionIRNodeVariant.UnlerpClamped),
    Remap(ir = FunctionIRNodeVariant.Remap),
    RemapClamped(ir = FunctionIRNodeVariant.RemapClamped),
    Smoothstep(ir = FunctionIRNodeVariant.Smoothstep),
    Random(ir = FunctionIRNodeVariant.Random),
    RandomInteger(ir = FunctionIRNodeVariant.RandomInteger),
    ConstexprRandom(ir = FunctionIRNodeVariant.ConstexprRandom),
    ConstexprRandomInteger(ir = FunctionIRNodeVariant.ConstexprRandomInteger),
    Get(ir = FunctionIRNodeVariant.Get),
    GetShifted(ir = FunctionIRNodeVariant.GetShifted),
    Set(false, FunctionIRNodeVariant.Set),
    SetShifted(false, FunctionIRNodeVariant.SetShifted),
    Draw(false, FunctionIRNodeVariant.Draw),
    Play(false, FunctionIRNodeVariant.Play),
    PlayScheduled(false, FunctionIRNodeVariant.PlayScheduled),
    Spawn(false, FunctionIRNodeVariant.Spawn),
    Judge(ir = FunctionIRNodeVariant.Judge),
    JudgeSimple(ir = FunctionIRNodeVariant.JudgeSimple),
    EaseInSine(ir = FunctionIRNodeVariant.EaseInSine),
    EaseOutSine(ir = FunctionIRNodeVariant.EaseOutSine),
    EaseInOutSine(ir = FunctionIRNodeVariant.EaseInOutSine),
    EaseInQuad(ir = FunctionIRNodeVariant.EaseInQuad),
    EaseOutQuad(ir = FunctionIRNodeVariant.EaseOutQuad),
    EaseInOutQuad(ir = FunctionIRNodeVariant.EaseInOutQuad),
    EaseInCubic(ir = FunctionIRNodeVariant.EaseInCubic),
    EaseOutCubic(ir = FunctionIRNodeVariant.EaseOutCubic),
    EaseInOutCubic(ir = FunctionIRNodeVariant.EaseInOutCubic),
    EaseInQuart(ir = FunctionIRNodeVariant.EaseInQuart),
    EaseOutQuart(ir = FunctionIRNodeVariant.EaseOutQuart),
    EaseInOutQuart(ir = FunctionIRNodeVariant.EaseInOutQuart),
    EaseInQuint(ir = FunctionIRNodeVariant.EaseInQuint),
    EaseOutQuint(ir = FunctionIRNodeVariant.EaseOutQuint),
    EaseInOutQuint(ir = FunctionIRNodeVariant.EaseInOutQuint),
    EaseInExpo(ir = FunctionIRNodeVariant.EaseInExpo),
    EaseOutExpo(ir = FunctionIRNodeVariant.EaseOutExpo),
    EaseInOutExpo(ir = FunctionIRNodeVariant.EaseInOutExpo),
    EaseInCirc(ir = FunctionIRNodeVariant.EaseInCirc),
    EaseOutCirc(ir = FunctionIRNodeVariant.EaseOutCirc),
    EaseInOutCirc(ir = FunctionIRNodeVariant.EaseInOutCirc),
    EaseInBack(ir = FunctionIRNodeVariant.EaseInBack),
    EaseOutBack(ir = FunctionIRNodeVariant.EaseOutBack),
    EaseInOutBack(ir = FunctionIRNodeVariant.EaseInOutBack),
    EaseInElastic(ir = FunctionIRNodeVariant.EaseInElastic),
    EaseOutElastic(ir = FunctionIRNodeVariant.EaseOutElastic),
    EaseInOutElastic(ir = FunctionIRNodeVariant.EaseInOutElastic),
    ;
}

/**
 * A builtin function.
 * Builtin functions are part of Sonolus.
 */
class BuiltinFunction(val function: BuiltinFunctionVariant) : Callable, Value {
    override val type = FunctionType

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val argumentValues = arguments.map {
            val parameterValue = it.value
            if (parameterValue !is RawStructValue) compileError("A builtin function must be called with only raw struct arguments.")
            parameterValue.value.toIR()
        }
        return if (function.returns) {
            val rawType = callingContext.scope.getFullyQualified(listOf("std", "Raw")) as StructDeclaration
            RawStructValue(BuiltinCallValue(function, argumentValues), callingContext, rawType)
        } else {
            if (callingContext !is ExecutionContext) compileError("Builtin function $function requires an execution context.")
            callingContext.statements += BuiltinCallValue(function, argumentValues)
            UnitValue
        }
    }
}
package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.expression.Callable
import xyz.qwewqa.trebla.frontend.expression.Statement
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.rawType

class ExecuteBuiltin(parentContext: Context, val projectConfiguration: CompilerConfiguration) :
    SimpleDeclaration(
        parentContext,
       "executeBuiltin",
        CallableType
    ),
    Callable by CallableDSL(
        parentContext,
        {
            "value" type context.rawType
        },
        {
            if (callingContext !is ExecutionContext) compileError("Builtin not executable in non-execution context.")
            callingContext.statements += Statement { "value".cast<RawStructValue>().raw.toIR() }
            UnitValue
        },
    )
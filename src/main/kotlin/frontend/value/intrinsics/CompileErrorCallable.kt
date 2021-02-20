package xyz.qwewqa.trebla.frontend.value.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.value.CallableType
import xyz.qwewqa.trebla.frontend.expression.Callable

class CompileErrorCallable(context: Context) :
    SimpleDeclaration(
        context,
        "compileError",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "message" type StringType default StringValue("")
        },
        {
            val message = "message".cast<StringValue>().value
            compileError(if (message.isBlank()) "Compile Error" else message)
        }
    )

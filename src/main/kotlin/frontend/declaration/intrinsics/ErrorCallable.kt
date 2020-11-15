package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.expression.Callable

class ErrorCallable(context: Context) :
    SimpleDeclaration(
        context,
        "error",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            "message" type StringType default StringValue("")
        },
        {
            val message = "message".cast<StringValue>().value
            compileError(if (message.isBlank()) "Error" else "Error: $message")
        }
    )

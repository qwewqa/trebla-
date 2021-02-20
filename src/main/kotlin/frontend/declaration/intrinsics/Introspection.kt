package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.Allocatable
import xyz.qwewqa.trebla.frontend.expression.Callable
import xyz.qwewqa.trebla.frontend.expression.Value

class SizeOfCallable(context: Context) :
        SimpleDeclaration(
            context,
            "sizeOf",
            CallableType
        ),
        Callable by CallableDelegate(
            {
                "type" type TypeType
            },
            {
                val type = "type".cast<Type>()
                if (type !is Allocatable) compileError("Type is not allocated")
                type.allocationSize.toPrimitive()
            }
        )

class TypeOfCallable(context: Context) :
    SimpleDeclaration(
        context,
        "typeOf",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "value" type AnyType
        },
        {
            "value".cast<Value>().type
        }
    )

class TypeAcceptsCallable(context: Context) :
    SimpleDeclaration(
        context,
        "typeAccepts",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "constraint" type TypeType
            "type" type TypeType
        },
        {
            val constraint = "constraint".cast<Type>()
            val type = "type".cast<Type>()
            (constraint includes type).toPrimitive()
        }
    )

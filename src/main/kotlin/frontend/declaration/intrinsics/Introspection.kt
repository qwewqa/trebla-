package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.declaration.TypeType
import xyz.qwewqa.trebla.frontend.expression.Allocatable
import xyz.qwewqa.trebla.frontend.expression.Callable
import xyz.qwewqa.trebla.frontend.expression.Value

class SizeOf(context: Context) :
        SimpleDeclaration(
            context,
            "sizeOf",
            CallableType
        ),
        Callable by CallableDelegate(
            context,
            {
                "type" type TypeType
            },
            {
                val type = "type".cast<Type>()
                if (type !is Allocatable) compileError("Type is not allocated")
                type.allocationSize.toStruct(callingContext)
            }
        )

class TypeOf(context: Context) :
    SimpleDeclaration(
        context,
        "typeOf",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            "value" type AnyType
        },
        {
            "value".cast<Value>().type
        }
    )

class TypeAccepts(context: Context) :
    SimpleDeclaration(
        context,
        "typeAccepts",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            "constraint" type TypeType
            "type" type TypeType
        },
        {
            val constraint = "constraint".cast<Type>()
            val type = "type".cast<Type>()
            constraint.accepts(type).toStruct(callingContext)
        }
    )

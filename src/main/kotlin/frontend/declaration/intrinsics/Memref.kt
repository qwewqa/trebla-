package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.ConcreteAllocation
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.getFullyQualified
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.declaration.StructDeclaration
import xyz.qwewqa.trebla.frontend.expression.AllocatedRawValue

class Memref(parentContext: Context, val projectConfiguration: CompilerConfiguration) :
    IntrinsicCallable by IntrinsicCallableDSL(
        parentContext,
        "memref",
        {
            "block" type NumberType
            "index" type NumberType
        },
        { callingContext ->
            val block = "block".number
            val index = "index".number
            if (block.toInt().toDouble() != block || index.toInt().toDouble() != index)
                compileError("memref arguments must be integers.")
            RawStructValue(
                AllocatedRawValue(ConcreteAllocation(block.toInt(), index.toInt())),
                callingContext,
                parentContext.scope.getFullyQualified("std", "Raw") as StructDeclaration
            )
        }
    )
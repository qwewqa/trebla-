package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.NumberType
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.DynamicAllocator
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.declaration.TypeType
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.frontend.fromRaw
import xyz.qwewqa.trebla.frontend.value.MapProxyInstance

class DereferenceCallable(context: Context) :
    SimpleDeclaration(
        context,
        "dereference",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "type" type TypeType
            "block" type NumberType
            "index" type NumberType
        },
        {
            val type = "type".cast<Type>()
            if (type !is Allocatable) compileError("Type ${type.commonName} is not allocatable.")
            val block = "block".cast<PrimitiveInstance>().value
            val index = "index".cast<PrimitiveInstance>().value
            type.allocateOn(DynamicAllocator(block, index), context)
        }
    )

class AllocationDataOf(context: Context) :
    SimpleDeclaration(
        context,
        "allocationDataOf",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "value" type AnyType
        },
        {
            val value = "value".cast<Value>()
            if (value !is Allocated) compileError("Type ${value.type.commonName} is not allocatable.")
            val (block, index) = value.allocationLocation() ?: compileError("Value is not contiguous or is not allocated.")
            MapProxyInstance("block" to NumberType.fromRaw(block), "index" to NumberType.fromRaw(index))
        }
    )

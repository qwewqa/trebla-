package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.context.Context

interface Bindable : Value {
    fun boundTo(value: Value, context: Context) : Value
}
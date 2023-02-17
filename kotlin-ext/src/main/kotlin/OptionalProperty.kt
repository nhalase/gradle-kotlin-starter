@file:Suppress("unused", "FunctionName")

package dev.nhalase.kotlin

sealed class OptionalProperty<out T> {

    object NotPresent : OptionalProperty<Nothing>()

    data class Present<T>(val value: T) : OptionalProperty<T>()
}

fun <T> OptionalProperty(value: T): OptionalProperty<T> = OptionalProperty.Present(value)

fun <T> op(value: T): OptionalProperty<T> = OptionalProperty(value)

fun <T> NullabeOptionalProperty(value: T?): OptionalProperty<T?> = OptionalProperty.Present(value)

fun <T> nop(value: T?): OptionalProperty<T?> = OptionalProperty(value)

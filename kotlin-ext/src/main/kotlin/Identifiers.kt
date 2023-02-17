@file:Suppress("unused")

package dev.nhalase.kotlin

import java.util.UUID

interface StringableIdentifier : Stringable

sealed interface Identifier<T> : StringableIdentifier {
    val value: T
}

interface StringIdentifier : Identifier<String>

interface UUIDIdentifier : Identifier<UUID>

interface CustomIdentifier<T> : Identifier<T>

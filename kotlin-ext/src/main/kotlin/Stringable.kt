@file:Suppress("unused")

package dev.nhalase.kotlin

interface Stringable {
    /**
     * Provides a String representation of this class. This provides an alternative to toString() for
     * implementation-specific use-cases (e.g., serialization, logging, etc.).
     *
     * Implementors should document their intended use of this function.
     */
    fun asString(): String
}

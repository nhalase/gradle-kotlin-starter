@file:Suppress("unused")

package dev.nhalase.kotlin

import java.util.UUID

const val NIL_UUID_TOKEN: String = "nil"
const val NIL_UUID_STRING: String = "00000000-0000-0000-0000-000000000000"
val nilUUIDProvider: () -> UUID = { UUID.fromString(NIL_UUID_STRING) }
val NIL_UUID: UUID by lazy { nilUUIDProvider() }

/**
 * Wrapper for Java's UUID class so Mockk tests can be written.
 * DO NOT USE THIS CLASS OUTSIDE TESTING PURPOSES.
 */
@Deprecated(message = "for testing purposes")
internal object JavaUUID {

    /**
     * Wrapper for [UUID.fromString]
     */
    fun fromString(name: String): UUID = UUID.fromString(name)

    /**
     * Wrapper for the [UUID] constructor accepting [mostSigBits] and [leastSigBits]
     */
    fun create(mostSigBits: Long, leastSigBits: Long): UUID = UUID(mostSigBits, leastSigBits)

    /**
     * Wrapper for [UUID.randomUUID]
     */
    fun randomUUID(): UUID = UUID.randomUUID()

    /**
     * Returns a constant nil [UUID]
     */
    fun nil(): UUID = NIL_UUID
}

open class InvalidUUIDStringException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)

class InvalidV4UUIDStringException(
    message: String? = null,
    cause: Throwable? = null
) : InvalidUUIDStringException(
    message = message,
    cause = cause
)

@kotlin.jvm.Throws(InvalidV4UUIDStringException::class)
fun uuidv4(fromString: String? = null): UUID {
    return if (fromString != null) {
        val equalsNilUuidString = fromString.equals(
            NIL_UUID_STRING,
            // UUID generators output in lowercase and systems accept UUIDs in upper and lowercase
            // https://www.rfc-editor.org/rfc/rfc4122#section-3
            ignoreCase = true
        )
        if (fromString.equals(NIL_UUID_TOKEN, ignoreCase = true) || equalsNilUuidString) {
            NIL_UUID
        } else {
            try {
                @Suppress("DEPRECATION")
                val uuid = JavaUUID.fromString(fromString)
                if (uuid.version() != 4) throw InvalidV4UUIDStringException("$fromString is not a v4 UUID")
                uuid
            } catch (e: IllegalArgumentException) {
                throw InvalidUUIDStringException("$fromString is not a UUID", e)
            }
        }
    } else {
        @Suppress("DEPRECATION")
        JavaUUID.randomUUID()
    }
}

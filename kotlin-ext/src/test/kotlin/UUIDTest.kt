package dev.nhalase.kotlin

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.UUID

@Suppress("DEPRECATION")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
// PER_METHOD is the default, but it is explicitly needed here due to mocking an object being important
class UUIDTest {

    @Test
    fun `assert uuidv4 does not use JavaUUID for nil token string`() {
        mockkObject(JavaUUID)
        val uuid = uuidv4(fromString = "nil")
        verify {
            JavaUUID.fromString(any()) wasNot Called
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), uuid)
    }

    @Test
    fun `assert uuidv4 does not use JavaUUID for nil token string and is case insensitive`() {
        mockkObject(JavaUUID)
        val uuid = uuidv4(fromString = "nIl")
        verify {
            JavaUUID.fromString(any()) wasNot Called
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), uuid)
    }

    @Test
    fun `assert uuidv4 does not use JavaUUID class for nil uuid string`() {
        mockkObject(JavaUUID)
        val uuid = uuidv4(fromString = "00000000-0000-0000-0000-000000000000")
        verify {
            JavaUUID.fromString(any()) wasNot Called
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), uuid)
    }

    @Test
    fun `assert uuidv4 uses JavaUUID fromString for v4 uuid string`() {
        mockkObject(JavaUUID)
        val uuid = uuidv4(fromString = "27c3e7c6-88dc-4fcb-a54d-234abc0901fc")
        verify(exactly = 1) { JavaUUID.fromString("27c3e7c6-88dc-4fcb-a54d-234abc0901fc") }
        verify {
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals(UUID.fromString("27c3e7c6-88dc-4fcb-a54d-234abc0901fc"), uuid)
    }

    @Test
    fun `assert uuidv4 uses JavaUUID randomUUID`() {
        mockkObject(JavaUUID)
        every { JavaUUID.randomUUID() } returns UUID.fromString("27c3e7c6-88dc-4fcb-a54d-234abc0901fc")
        val uuid = uuidv4()
        verify(exactly = 1) { JavaUUID.randomUUID() }
        verify {
            JavaUUID.fromString(any()) wasNot Called
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals(UUID.fromString("27c3e7c6-88dc-4fcb-a54d-234abc0901fc"), uuid)
    }

    @Test
    fun `assert uuidv4 throws InvalidV4UUIDStringException if fromString is not a v4 UUID`() {
        mockkObject(JavaUUID)
        val exception = assertThrows<InvalidV4UUIDStringException> {
            uuidv4(
                fromString = "d90ecefc-af0d-11ed-afa1-0242ac120002" // Version 1 UUID
            )
        }
        verify(exactly = 1) { JavaUUID.fromString("d90ecefc-af0d-11ed-afa1-0242ac120002") }
        verify {
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals("d90ecefc-af0d-11ed-afa1-0242ac120002 is not a v4 UUID", exception.message)
    }

    @Test
    fun `assert uuidv4 throws InvalidUUIDStringException if fromString is not a UUID`() {
        mockkObject(JavaUUID)
        val exception = assertThrows<InvalidUUIDStringException> {
            uuidv4(fromString = "not-a-uuid")
        }
        verify(exactly = 1) { JavaUUID.fromString("not-a-uuid") }
        verify {
            JavaUUID.create(any(), any()) wasNot Called
            JavaUUID.randomUUID() wasNot Called
            JavaUUID.nil() wasNot Called
        }
        unmockkObject(JavaUUID)
        assertEquals("not-a-uuid is not a UUID", exception.message)
        assertEquals("Invalid UUID string: not-a-uuid", exception.cause?.message)
        assertTrue(exception.cause is IllegalArgumentException)
    }
}

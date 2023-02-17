@file:Suppress("unused")

package dev.nhalase.kotlin

import java.nio.charset.Charset
import java.util.Base64
import java.util.UUID

internal val base64Encoder: Base64.Encoder = Base64.getEncoder()
internal val base64Decoder: Base64.Decoder = Base64.getDecoder()

fun UUID.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray {
    return toString().toByteArray(charset = charset)
}

fun String.base64Encode(charset: Charset = Charsets.UTF_8): String {
    return base64Encoder.encodeToString(this.toByteArray(charset = charset))
}

fun ByteArray.base64Encode(): String {
    return base64Encoder.encodeToString(this)
}

fun String.base64Decode(charset: Charset = Charsets.UTF_8): String {
    return String(bytes = base64Decoder.decode(this), charset = charset)
}

fun String.tryBase64Decode(charset: Charset = Charsets.UTF_8): String {
    val bytes = try {
        base64Decoder.decode(this)
    } catch (e: Exception) {
        null
    }
    return if (bytes != null) {
        String(bytes = bytes, charset = charset)
    } else {
        this
    }
}

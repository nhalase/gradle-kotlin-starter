@file:OptIn(ExperimentalSerializationApi::class)
@file:Suppress("unused")

package dev.nhalase.kotlin

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.MonthDay
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@Serializer(forClass = OptionalProperty::class)
class OptionalPropertySerializer<T>(
    private val valueSerializer: KSerializer<T>
) : KSerializer<OptionalProperty<T>> {
    override val descriptor: SerialDescriptor = valueSerializer.descriptor

    override fun deserialize(decoder: Decoder): OptionalProperty<T> =
        OptionalProperty.Present(valueSerializer.deserialize(decoder))

    override fun serialize(encoder: Encoder, value: OptionalProperty<T>) {
        val errorMessage = "Tried to serialize an optional property that had no value present. Is encodeDefaults false?"
        when (value) {
            OptionalProperty.NotPresent -> throw SerializationException(errorMessage)
            is OptionalProperty.Present -> valueSerializer.serialize(encoder, value.value)
        }
    }
}

@Serializer(forClass = UUID::class)
object UUIDAsStringSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeAsIso8601StringSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(decoder.decodeString()))
    }
}

@Serializer(forClass = LocalDate::class)
object LocalDateAsIso8601StringSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(decoder.decodeString()))
    }
}

@Serializer(forClass = Instant::class)
object InstantAsIso8601StringSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(DateTimeFormatter.ISO_INSTANT.format(value))
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(decoder.decodeString()))
    }
}

@Serializer(forClass = BigDecimal::class)
object BigDecimalAsStringSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeString())
    }
}

@Serializer(forClass = ZoneId::class)
object ZoneIdAsStringSerializer : KSerializer<ZoneId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ZoneId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZoneId) {
        encoder.encodeString(value.id)
    }

    override fun deserialize(decoder: Decoder): ZoneId {
        return ZoneId.of(decoder.decodeString())
    }
}

@Serializer(forClass = Locale::class)
object LocaleAsStringSerializer : KSerializer<Locale> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Locale", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Locale) {
        encoder.encodeString(value.toLanguageTag())
    }

    override fun deserialize(decoder: Decoder): Locale {
        return Locale.forLanguageTag(decoder.decodeString())
    }
}

@Serializer(forClass = Year::class)
object YearAsIntSerializer : KSerializer<Year> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Year", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Year) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): Year {
        return Year.of(decoder.decodeInt())
    }
}

@Serializer(forClass = Year::class)
object YearAsStringSerializer : KSerializer<Year> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Year", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Year) {
        encoder.encodeString(value.value.toString())
    }

    override fun deserialize(decoder: Decoder): Year {
        return Year.of(decoder.decodeString().toInt())
    }
}

@Serializer(forClass = YearMonth::class)
object YearMonthAsStringSerializer : KSerializer<YearMonth> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("YearMonth", PrimitiveKind.STRING)
    val yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

    override fun serialize(encoder: Encoder, value: YearMonth) {
        encoder.encodeString(value.format(yearMonthFormatter))
    }

    override fun deserialize(decoder: Decoder): YearMonth {
        val string = decoder.decodeString()
        return YearMonth.parse(string)
    }
}

@Serializer(forClass = MonthDay::class)
object MonthDayAsStringSerializer : KSerializer<MonthDay> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("MonthDay", PrimitiveKind.STRING)
    val monthDayFormatter = DateTimeFormatter.ofPattern("MM-dd")

    override fun serialize(encoder: Encoder, value: MonthDay) {
        encoder.encodeString(value.format(monthDayFormatter))
    }

    override fun deserialize(decoder: Decoder): MonthDay {
        val string = decoder.decodeString()
        return MonthDay.from(monthDayFormatter.parse(string))
    }
}

@Suppress("RedundantVisibilityModifier", "FunctionName", "DuplicatedCode")
public inline fun CommonSerializersModule(builderAction: SerializersModuleBuilder.() -> Unit = {}): SerializersModule {
    return SerializersModule {
        contextual(UUID::class, UUIDAsStringSerializer)
        contextual(LocalDateTime::class, LocalDateTimeAsIso8601StringSerializer)
        contextual(LocalDate::class, LocalDateAsIso8601StringSerializer)
        contextual(Instant::class, InstantAsIso8601StringSerializer)
        contextual(BigDecimal::class, BigDecimalAsStringSerializer)
        contextual(ZoneId::class, ZoneIdAsStringSerializer)
        contextual(Locale::class, LocaleAsStringSerializer)
        contextual(Year::class, YearAsIntSerializer)
        contextual(YearMonth::class, YearMonthAsStringSerializer)
        contextual(MonthDay::class, MonthDayAsStringSerializer)
        builderAction()
    }
}

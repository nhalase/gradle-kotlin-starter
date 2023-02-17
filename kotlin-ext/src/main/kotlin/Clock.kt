package dev.nhalase.kotlin

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.Year
import java.time.ZonedDateTime
import java.time.ZoneId

fun getCurrentYear(zoneId: ZoneId): Year = Year.now(zoneId)

fun getCurrentZonedDateTime(zoneId: ZoneId): ZonedDateTime = ZonedDateTime.now(zoneId)

fun getCurrentInstant(zoneId: ZoneId): Instant = Instant.now(Clock.system(zoneId))

fun getCurrentLocalDate(zoneId: ZoneId): LocalDate = LocalDate.now(zoneId)

fun getCurrentLocalDateTime(zoneId: ZoneId): LocalDateTime = LocalDateTime.now(zoneId)

fun getCurrentOffsetDateTime(zoneId: ZoneId): OffsetDateTime = OffsetDateTime.now(zoneId)

fun getCurrentLocalTime(zoneId: ZoneId): LocalTime = LocalTime.now(zoneId)

fun getCurrentMonth(zoneId: ZoneId): Month = LocalDate.now(zoneId).month

fun getCurrentMonthDay(zoneId: ZoneId): MonthDay = MonthDay.now(zoneId)

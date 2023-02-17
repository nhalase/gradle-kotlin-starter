@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.nhalase.kotlin

import java.time.Year

class YearRange(start: Year, endInclusive: Year) : YearProgression(start, endInclusive, 1), ClosedRange<Year> {
    override val start: Year get() = first
    override val endInclusive: Year get() = last

    override fun contains(value: Year): Boolean = value in first..last

    /**
     * Checks whether the range is empty.
     *
     * The range is empty if its start value is greater than the end value.
     */
    override fun isEmpty(): Boolean = first > last

    override fun equals(other: Any?): Boolean =
        other is YearRange && (
            isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last
            )

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * first.value + last.value)

    override fun toString(): String = "${first.value}..${last.value}"
}

open class YearProgression internal constructor(
    start: Year,
    endInclusive: Year,
    /**
     * The step of the progression.
     */
    val step: Int
) : Iterable<Year> {
    init {
        if (step == 0) throw kotlin.IllegalArgumentException("Step must be non-zero.")
        if (step == Int.MIN_VALUE) throw kotlin.IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.")
    }

    /**
     * The first element in the progression.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val first: Year = start

    /**
     * The last element in the progression.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val last: Year = getProgressionLastElement(start, endInclusive, step)

    override fun iterator(): YearIterator = YearProgressionIterator(first, last, step)

    /**
     * Checks if the progression is empty.
     *
     * Progression with a positive step is empty if its first element is greater than the last element.
     * Progression with a negative step is empty if its first element is less than the last element.
     */
    open fun isEmpty(): Boolean = if (step > 0) first > last else first < last

    override fun equals(other: Any?): Boolean =
        other is YearProgression && (
            isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step
            )

    override fun hashCode(): Int =
        if (isEmpty()) -1 else (31 * (31 * first.value + last.value) + step)

    override fun toString(): String =
        if (step > 0) "${first.value}..${last.value} step $step" else "${first.value} downTo ${last.value} step ${-step}"

    companion object {
        /**
         * Creates IntProgression within the specified bounds of a closed range.
         *
         * The progression starts with the [rangeStart] value and goes toward the [rangeEnd] value not excluding it, with the specified [step].
         * In order to go backwards the [step] must be negative.
         *
         * [step] must be greater than `Int.MIN_VALUE` and not equal to zero.
         */
        fun fromClosedRange(
            rangeStart: Year,
            rangeEnd: Year,
            step: Int
        ): YearProgression = YearProgression(rangeStart, rangeEnd, step)
    }
}

/** An iterator over a sequence of values of type `Year`. */
abstract class YearIterator : Iterator<Year> {
    final override fun next() = nextYear()

    /** Returns the next value in the sequence without boxing. */
    abstract fun nextYear(): Year
}

/**
 * An iterator over a progression of values of type `Year`.
 * @property step the number by which the value is incremented on each step.
 */
internal class YearProgressionIterator(first: Year, last: Year, val step: Int) : YearIterator() {
    private val finalElement: Year = last
    private var hasNext: Boolean = if (step > 0) first <= last else first >= last
    private var next: Year = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun nextYear(): Year {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        } else {
            next = next.plusYears(step.toLong())
        }
        return value
    }
}

/**
 * Calculates the final element of a bounded arithmetic progression, i.e. the last element of the progression which is in the range
 * from [start] to [end] in case of a positive [step], or from [end] to [start] in case of a negative
 * [step].
 *
 * No validation on passed parameters is performed. The given parameters should satisfy the condition:
 *
 * - either `step > 0` and `start <= end`,
 * - or `step < 0` and `start >= end`.
 *
 * @param start first element of the progression
 * @param end ending bound for the progression
 * @param step increment, or difference of successive elements in the progression
 * @return the final element of the progression
 * @suppress
 */
private fun getProgressionLastElement(start: Year, end: Year, step: Int): Year = when {
    step > 0 -> if (start >= end) end else end.minusYears(differenceModulo(end, start, step).toLong())
    step < 0 -> if (start <= end) end else end.plusYears(differenceModulo(start, end, -step).toLong())
    else -> throw kotlin.IllegalArgumentException("Step is zero.")
}

private fun differenceModulo(a: Year, b: Year, c: Int): Int {
    return mod(mod(a.value, c) - mod(b.value, c), c)
}

private fun mod(a: Int, b: Int): Int {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun getYearRange(startYear: Year?, endYearInclusive: Year?): YearRange? {
    if (startYear == null && endYearInclusive == null) return null
    require(startYear != null && endYearInclusive != null) {
        "both startYear AND endYearInclusive are required"
    }
    return YearRange(start = startYear, endInclusive = endYearInclusive)
}

@kotlin.jvm.Throws(IllegalArgumentException::class)
fun getYearRange(startYear: Int?, endYearInclusive: Int?): YearRange? =
    getYearRange(startYear = startYear?.let { Year.of(it) }, endYearInclusive = endYearInclusive?.let { Year.of(it) })

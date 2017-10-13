package iii_conventions

import java.time.LocalDate
import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class MyDateIterator(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {
    private var currentDate = start
    override fun hasNext(): Boolean {
        return currentDate <= endInclusive
    }

    override fun next(): MyDate {
        val date = currentDate
        currentDate = currentDate.nextDay()
        return date
    }

}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator() = MyDateIterator(start, endInclusive)
}


operator fun MyDate.plus(interval: TimeInterval): MyDate = this.addTimeIntervals(interval, 1)

class MulTimeInterval(val timeInterval: TimeInterval, val mul: Int)

operator fun TimeInterval.times(i: Int) = MulTimeInterval(this, i)

operator fun MyDate.plus(mulTimeInterval: MulTimeInterval): MyDate = this.addTimeIntervals(mulTimeInterval.timeInterval, mulTimeInterval.mul)
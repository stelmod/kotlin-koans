package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.compareTo(other: MyDate): Int =
        if (this.year == other.year)
            if (this.month == other.month)
                this.dayOfMonth - other.dayOfMonth
            else this.month - other.month
        else this.year - other.year

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(repeatedTimeInterval.ti, repeatedTimeInterval.n)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, n)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate)

operator fun DateRange.contains(date: MyDate): Boolean = date >= start && date <= endInclusive

operator fun DateRange.iterator(): Iterator<MyDate> {
    return object: Iterator<MyDate> {
        var current = start

        override operator fun next(): MyDate {
            val ret = current
            current = current.nextDay()
            return ret
        }

        override operator fun hasNext(): Boolean = contains(current)

    }
}
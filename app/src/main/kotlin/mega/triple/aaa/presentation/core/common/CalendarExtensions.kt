package mega.triple.aaa.presentation.core.common

import java.util.Calendar

fun Calendar.getBeginningTime(): Long = apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.timeInMillis

fun Calendar.getEndTime(): Long = apply {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 999)
}.timeInMillis

fun Long.isToday(): Boolean {
    val calendar = Calendar.getInstance()

    return this in calendar.dayRange()
}

fun Long.isTomorrow(): Boolean {
    val calendar = Calendar.getInstance().apply { this.add(Calendar.DAY_OF_MONTH, 1) }

    return this in calendar.dayRange()
}

fun Calendar.dayRange(): LongRange = getBeginningTime()..getEndTime()

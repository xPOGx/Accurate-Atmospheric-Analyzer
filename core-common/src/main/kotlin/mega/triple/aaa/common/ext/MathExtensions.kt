package mega.triple.aaa.common.ext

import android.icu.util.Calendar
import android.icu.util.TimeZone
import mega.triple.aaa.common.ext.Constants.HOUR_SHORT
import mega.triple.aaa.common.ext.Constants.MINUTE_SHORT
import mega.triple.aaa.common.ext.Constants.TIME_ZONE_UA

fun diff(value1: Double?, value2: Double?) =
    value2?.let {
        value1?.minus(value2).takeIf { it != 0.0 }
    }

fun diff(value1: Int?, value2: Int?) =
    value2?.let {
        value1?.minus(value2).takeIf { it != 0 }
    }

fun getTimeDiff(value: Long?): String {
    val other = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone(TIME_ZONE_UA)
        value?.let { timeInMillis = value.normalized() }
    }
    val now = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone(TIME_ZONE_UA)
    }
    val otherMinute = other.timeInMillis / 1000 / 60
    val otherHour = otherMinute / 60
    val nowMinute = now.timeInMillis / 1000 / 60
    val nowHour = nowMinute / 60
    return if (other.timeInMillis > now.timeInMillis) {
        var diff = otherHour - nowHour
        val symbol = if (diff > 0) {
            HOUR_SHORT
        } else {
            diff = otherMinute - nowMinute
            MINUTE_SHORT
        }
        "in $diff$symbol"
    } else {
        var diff = nowHour - otherHour
        val symbol = if (diff > 0) {
            HOUR_SHORT
        } else {
            diff = nowMinute - otherMinute
            MINUTE_SHORT
        }
        "$diff$symbol ago"
    }
}

package mega.triple.aaa.common.ext

import android.icu.util.Calendar
import android.icu.util.TimeZone
import mega.triple.aaa.common.ext.Constants.HOUR_SHORT
import mega.triple.aaa.common.ext.Constants.MINUTE_SHORT
import mega.triple.aaa.common.ext.Constants.TIME_ZONE_UA

fun diff(value1: Double?, value2: Double?) =
    value1?.let {
        value2?.let {
            value1.minus(value2).takeIf { it != 0.0 }
        }
    }

fun diff(value1: Int?, value2: Int?) =
    value1?.let {
        value2?.let {
            value1.minus(value2).takeIf { it != 0 }
        }
    }

fun getTimeDiff(value: Long?): String {
    val other = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone(TIME_ZONE_UA)
        value?.let { timeInMillis = value * 1000 } // WHY?!?!?!?!??! in seconds.......
    }
    val now = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone(TIME_ZONE_UA)
    }
    val otherHour = other.get(Calendar.HOUR_OF_DAY)
    val nowHour = now.get(Calendar.HOUR_OF_DAY)
    return if (other.timeInMillis > now.timeInMillis) {
        var diff = otherHour - nowHour
        val symbol = if (diff > 0) {
            HOUR_SHORT
        } else {
            diff = other.get(Calendar.MINUTE) - now.get(Calendar.MINUTE)
            MINUTE_SHORT
        }
        "in $diff$symbol"
    } else {
        var diff = nowHour - otherHour
        val symbol = if (diff > 0) {
            HOUR_SHORT
        } else {
            diff = now.get(Calendar.MINUTE) - other.get(Calendar.MINUTE)
            MINUTE_SHORT
        }
        "$diff$symbol ago"
    }
}

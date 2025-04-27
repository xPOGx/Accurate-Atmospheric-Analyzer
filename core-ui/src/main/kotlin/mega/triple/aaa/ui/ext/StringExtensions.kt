package mega.triple.aaa.ui.ext

import android.content.Context
import mega.triple.aaa.common.ext.Constants
import mega.triple.aaa.strings.R.string
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun formatPartTemperature(
    context: Context,
    value: Double?,
    isDay: Boolean,
    unit: String?,
): String = buildString {
    append(if (isDay) context.getString(string.common_day) else context.getString(string.common_night))
    append(Constants.SPACE)
    append(formatTemperature(value, unit))
}

fun formatTemperature(value: Double?, unit: String?): String = buildString {
    append(value?.roundToInt() ?: Constants.STUB_VALUE)
    append(temperatureSymbol(unit))
}

fun formatFeelTemperature(
    context: Context,
    value: Double?,
    unit: String?,
    inShadow: Boolean = false,
): String = buildString {
    append(if (inShadow) context.getString(string.toolbar_in_shadow) else context.getString(string.toolbar_feels_like))
    append(Constants.SPACE)
    append(value?.roundToInt() ?: Constants.STUB_VALUE)
    append(temperatureSymbol(unit))
}

fun formatTime(value: String?): String {
    return try {
        value ?: throw Throwable()
        val date = SimpleDateFormat(Constants.ISO_PATTERN, Locale.getDefault()).parse(value)
        date ?: throw Throwable()
        SimpleDateFormat(Constants.UI_PATTERN, Locale.getDefault()).format(date)
    } catch (_: Throwable) {
        val formatter = SimpleDateFormat(Constants.UI_PATTERN, Locale.getDefault())
        formatter.format(Date())
    }
}

fun formatSimpleTime(value: String?): String {
    return try {
        value ?: throw Throwable()
        val date = SimpleDateFormat(Constants.ISO_PATTERN, Locale.getDefault()).parse(value)
        date ?: throw Throwable()
        SimpleDateFormat(Constants.SIMPLE_PATTERN, Locale.getDefault()).format(date)
    } catch (_: Throwable) {
        Constants.STUB_VALUE
    }
}

fun formatDate(value: Long): String {
    return try {
        SimpleDateFormat(Constants.DATE_PATTEN, Locale.getDefault()).format(Date(value))
    } catch (_: Throwable) {
        Constants.STUB_VALUE
    }
}

fun formatShortDate(value: Long): String {
    return try {
        SimpleDateFormat(Constants.SHORT_DATE_PATTEN, Locale.getDefault())
            .format(Date(value))
            .replaceFirstChar { it.uppercase() }
    } catch (_: Throwable) {
        Constants.STUB_VALUE
    }
}

fun formatSpeed(value: Double?, unit: String?) = buildString {
    append(value?.roundToInt() ?: Constants.STUB_VALUE)
    append(unit)
}

fun formatProbability(value: Int?) = buildString {
    append(value ?: Constants.STUB_VALUE)
    append(Constants.PERCENTAGE)
}

fun formatLastUpdateTime(context: Context, date: Calendar?): String {
    val format: NumberFormat = DecimalFormat("00")
    return when {
        date == null -> context.getString(string.pull_to_refresh_first_time)
        else -> buildString {
            append(context.getString(string.pull_to_refresh_last_time))
            append(
                listOf(
                    date[Calendar.HOUR_OF_DAY],
                    date[Calendar.MINUTE],
                ).joinToString(Constants.COLON)
            )
            append(Constants.SPACE)
            append(
                listOf(
                    format.format(date[Calendar.DAY_OF_MONTH]),
                    format.format(date[Calendar.MONTH + 1]),
                    date[Calendar.YEAR],
                ).joinToString(Constants.PERIOD)
            )
        }
    }
}

private fun temperatureSymbol(value: String?) = when (value) {
    Constants.CELSIUS -> Constants.CELSIUS_SYMBOL
    else -> Constants.FAHRENHEIT
}

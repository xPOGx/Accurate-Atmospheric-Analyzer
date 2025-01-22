package mega.triple.aaa.ui.ext

import android.content.Context
import mega.triple.aaa.strings.R
import mega.triple.aaa.common.ext.Constants
import mega.triple.aaa.common.ext.Constants.CELSIUS
import mega.triple.aaa.common.ext.Constants.CELSIUS_SYMBOL
import mega.triple.aaa.common.ext.Constants.DATE_PATTEN
import mega.triple.aaa.common.ext.Constants.FAHRENHEIT
import mega.triple.aaa.common.ext.Constants.PERCENTAGE
import mega.triple.aaa.common.ext.Constants.SPACE
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun formatPartTemperature(
    context: Context,
    value: Double?,
    isDay: Boolean,
    unit: String?,
): String = buildString {
    append(if (isDay) context.getString(R.string.toolbar_day) else context.getString(R.string.toolbar_night))
    append(SPACE)
    append(formatTemperature(value, unit))
}

fun formatTemperature(value: Double?, unit: String?): String = buildString {
    append(value?.roundToInt() ?: STUB_VALUE)
    append(temperatureSymbol(unit))
}

fun formatFeelTemperature(
    context: Context,
    value: Double?,
    unit: String?,
    inShadow: Boolean = false,
): String = buildString {
    append(if (inShadow) context.getString(R.string.toolbar_in_shadow) else context.getString(R.string.toolbar_feels_like))
    append(SPACE)
    append(value?.roundToInt() ?: STUB_VALUE)
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
        STUB_VALUE
    }
}

fun formatDate(value: Long): String {
    return try {
        SimpleDateFormat(DATE_PATTEN, Locale.getDefault()).format(Date(value))
    } catch (_: Throwable) {
        STUB_VALUE
    }
}

fun formatSpeed(value: Double?, unit: String?) = buildString {
    append(value?.roundToInt() ?: STUB_VALUE)
    append(unit)
}

fun formatProbability(value: Int?) = buildString {
    append(value ?: STUB_VALUE)
    append(PERCENTAGE)
}

private fun temperatureSymbol(value: String?) = when (value) {
    CELSIUS -> CELSIUS_SYMBOL
    else -> FAHRENHEIT
}

package mega.triple.aaa.presentation.core.common

import mega.triple.aaa.presentation.core.common.Constants.PERCENTAGE
import mega.triple.aaa.presentation.core.common.Constants.SPACE
import mega.triple.aaa.presentation.core.common.Constants.STUB_VALUE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

// TODO: constants; move
fun formatPartTemperature(
    value: Double?,
    isDay: Boolean,
    unit: String?,
): String = buildString {
    append(if (isDay) "Day" else "Night")
    append(SPACE)
    append(formatTemperature(value, unit))
}

// TODO: constants; move
fun formatTemperature(value: Double?, unit: String?): String = buildString {
    append(value?.roundToInt() ?: STUB_VALUE)
    append(temperatureSymbol(unit))
}

// TODO: constants; move
fun formatFeelTemperature(
    value: Double?,
    unit: String?,
    inShadow: Boolean = false,
): String = buildString {
    append(if (inShadow) "In shadow" else "Feels like")
    append(SPACE)
    append(value?.roundToInt() ?: STUB_VALUE)
    append(temperatureSymbol(unit))
}

// TODO: constants; move
fun formatTime(value: String?): String {
    return try {
        val date = SimpleDateFormat(Constants.ISO_PATTERN, Locale.getDefault()).parse(value)
        SimpleDateFormat(Constants.UI_PATTERN, Locale.getDefault()).format(date)
    } catch (_: Throwable) {
        val formatter = SimpleDateFormat(Constants.UI_PATTERN, Locale.getDefault())
        formatter.format(Date())
    }
}

fun formatSimpleTime(value: String?): String {
    return try {
        val date = SimpleDateFormat(Constants.ISO_PATTERN, Locale.getDefault()).parse(value)
        SimpleDateFormat(Constants.SIMPLE_PATTERN, Locale.getDefault()).format(date)
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

private fun temperatureSymbol(value: String?) =
    if (value == "C") Constants.CELSIUS else Constants.FAHRENHEIT

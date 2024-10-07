package mega.triple.aaa.presentation.core.ui.ext

import mega.triple.aaa.presentation.core.common.Constants.CELSIUS
import mega.triple.aaa.presentation.core.common.Constants.FAHRENHEIT
import mega.triple.aaa.presentation.core.common.Constants.SPACE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: constants; move
fun formatPartTemperature(
    value: Int, isDay: Boolean, normalPeople: Boolean = true
): String = buildString {
    append(if (isDay) "Day" else "Night")
    append(SPACE)
    append(formatTemperature(value, normalPeople))
}

// TODO: constants; move
fun formatTemperature(value: Int, normalPeople: Boolean = true): String = buildString {
    append(value)
    append(temperatureSymbol(normalPeople))
}

// TODO: constants; move
fun formatFeelTemperature(
    value: Int,
    normalPeople: Boolean = true,
    inShadow: Boolean = false,
): String = buildString {
    append(if (inShadow) "In shadow" else "Feels like")
    append(SPACE)
    append(value)
    append(temperatureSymbol(normalPeople))
}

// TODO: constants; move
fun formatTime(value: Date): String {
    val formatter = SimpleDateFormat("MMMM d, HH:mm", Locale.getDefault())
    return formatter.format(value)
}

private fun temperatureSymbol(normalPeople: Boolean) = if (normalPeople) CELSIUS else FAHRENHEIT

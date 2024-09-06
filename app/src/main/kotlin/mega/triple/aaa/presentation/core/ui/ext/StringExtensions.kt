package mega.triple.aaa.presentation.core.ui.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: constants; move
fun formatPartTemperature(
    value: Int, isDay: Boolean, normalPeople: Boolean = true
): String = buildString {
    append(if (isDay) "Day" else "Night")
    append(" ")
    append(formatTemperature(value, normalPeople))
}

// TODO: constants; move
fun formatTemperature(value: Int, normalPeople: Boolean = true): String = buildString {
    append(value)
    append(
        if (normalPeople) '°' else 'F'
    )
}

// TODO: constants; move
fun formatFeelTemperature(value: Int, normalPeople: Boolean = true): String = buildString {
    append("Feels like ")
    append(value)
    append(
        if (normalPeople) '°' else 'F'
    )
}

// TODO: constants; move
fun formatTime(value: Date): String {
    val formatter = SimpleDateFormat("MMMM d, HH:mm", Locale.getDefault())
    return formatter.format(value)
}
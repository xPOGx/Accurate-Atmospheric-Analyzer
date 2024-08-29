package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.triple.aaa.presentation.core.ui.theme.lightColors

data class Borders(
    val primaryBorder: BorderStroke = BorderStroke(1.dp, lightColors().primary),
) {
    fun custom(width: Dp, color: Color): BorderStroke = BorderStroke(width, color)
}

val LocalBorders = staticCompositionLocalOf { Borders() }

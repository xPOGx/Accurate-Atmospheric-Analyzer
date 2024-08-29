package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import mega.triple.aaa.presentation.core.ui.theme.lightColors

class Colors(
    primary: Color,
    secondary: Color,
    tertiary: Color,
    background: Color,
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var tertiary by mutableStateOf(tertiary)
        private set

    var background by mutableStateOf(background)
        private set
}

val LocalColors = staticCompositionLocalOf { lightColors() }

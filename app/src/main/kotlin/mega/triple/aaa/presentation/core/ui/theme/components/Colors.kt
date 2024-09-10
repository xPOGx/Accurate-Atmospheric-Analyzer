package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import mega.triple.aaa.presentation.core.ui.theme.lightColors

class Colors(
    background: Color,
    white: Color,
    black: Color,
    toolbarBG: Color,
    tabContainer: Color,
    tabContent: Color,
    cardBG: Color,
    cardContent: Color,
    changeGrowth: Color,
    changeDecrease: Color,
) {
    var background by mutableStateOf(background)
        private set

    var white by mutableStateOf(white)
        private set

    var black by mutableStateOf(black)
        private set

    var toolbarBG by mutableStateOf(toolbarBG)
        private set

    var tabContainer by mutableStateOf(tabContainer)
        private set

    var tabContent by mutableStateOf(tabContent)
        private set

    var cardBG by mutableStateOf(cardBG)
        private set

    var cardContent by mutableStateOf(cardContent)
        private set

    var changeGrowth by mutableStateOf(changeGrowth)
        private set

    var changeDecrease by mutableStateOf(changeDecrease)
        private set
}

val LocalColors = staticCompositionLocalOf { lightColors() }

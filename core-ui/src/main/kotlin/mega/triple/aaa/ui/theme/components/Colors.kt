package mega.triple.aaa.ui.theme.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import mega.triple.aaa.ui.theme.lightColors

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
    secondaryText: Color,
    rainChance: Color,
    error: Color,
    chartArea: Color,
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

    var secondaryText by mutableStateOf(secondaryText)
        private set

    var rainChance by mutableStateOf(rainChance)
        private set

    var error by mutableStateOf(error)
        private set

    var chartArea by mutableStateOf(chartArea)
        private set
}

val LocalColors = staticCompositionLocalOf { lightColors() }

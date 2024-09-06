package mega.triple.aaa.presentation.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import mega.triple.aaa.presentation.core.ui.theme.components.Colors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalColors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalShapes
import mega.triple.aaa.presentation.core.ui.theme.components.LocalSpaces
import mega.triple.aaa.presentation.core.ui.theme.components.LocalTypography
import mega.triple.aaa.presentation.core.ui.theme.components.Shapes
import mega.triple.aaa.presentation.core.ui.theme.components.Spaces
import mega.triple.aaa.presentation.core.ui.theme.components.Typography

fun lightColors() =
    Colors(
        primary = Color.Red,
        secondary = Color.Green,
        tertiary = Color.Blue,
        white = Color.White,
        black = Color.Black,
        background = Color(0xFFF6EDFF),
        toolbarBG = Color(0xFFE2D3FA),
        cardBG = Color(0xFFD0BCFF).copy(alpha = 0.3f),
        cardContent = Color(0xFF1E1B1B),
        changeGrowth = Color(0xFF8A20D5),
        changeDecrease = Color(0xFFBA1A1A),
        tabContainer = Color(0xFFE0B6FF),
        tabContent = Color(0xFF2E004E),
    )

// TODO invert of something
fun darkColors() = lightColors()

@Composable
fun AAATheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    typography: Typography = AAATheme.typography,
    spaces: Spaces = AAATheme.spaces,
    shapes: Shapes = AAATheme.shapes,
    content: @Composable () -> Unit,
) {
    val colorsTheme = if (isDarkMode) darkColors() else lightColors()
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorsTheme.background.toArgb()
            window.navigationBarColor = colorsTheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalColors provides colorsTheme,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        content = content,
    )
}

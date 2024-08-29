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
import mega.triple.aaa.presentation.core.ui.theme.components.Borders
import mega.triple.aaa.presentation.core.ui.theme.components.Colors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalBorders
import mega.triple.aaa.presentation.core.ui.theme.components.LocalColors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalShapes
import mega.triple.aaa.presentation.core.ui.theme.components.LocalSpaces
import mega.triple.aaa.presentation.core.ui.theme.components.LocalTypography
import mega.triple.aaa.presentation.core.ui.theme.components.Shapes
import mega.triple.aaa.presentation.core.ui.theme.components.Spaces
import mega.triple.aaa.presentation.core.ui.theme.components.Typography

fun lightColors() =
    Colors(
        primary = Color(0xFFE30713),
        secondary = Color(0xFFFFFFFF),
        tertiary = Color(0xFF1D1B20),
        background = Color(0xFFF3F7FB),
    )

fun darkColors() =
    Colors(
        primary = Color(0xFFE30713),
        secondary = Color(0xFFFFFFFF),
        tertiary = Color(0xFF1D1B20),
        background = Color(0xFFF3F7FB),
    )

@Composable
fun AAATheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    typography: Typography = AAATheme.typography,
    spaces: Spaces = AAATheme.spaces,
    shapes: Shapes = AAATheme.shapes,
    borders: Borders = AAATheme.borders,
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
        LocalBorders provides borders,
        content = content,
    )
}
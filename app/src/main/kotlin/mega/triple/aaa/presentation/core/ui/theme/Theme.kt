package mega.triple.aaa.presentation.core.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
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
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType

fun lightColors() =
    Colors(
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
        secondaryText = Color(0xFF494649),
    )

fun darkColors() =
    Colors(
        white = Color(0xFF1E1B1B), // Text color (previously black)
        black = Color.White, // Background color (previously white)
        background = Color(0xFF121212), // Background for most elements
        toolbarBG = Color(0xFF1C1C1C), // Toolbar background
        cardBG = Color(0xFF333333), // Card background (darker than toolbar)
        cardContent = Color.White, // Text within cards (previously white)
        changeGrowth = Color(0xFF2ECC71), // Positive change color (green)
        changeDecrease = Color(0xFFF52424), // Negative change color (red)
        tabContainer = Color(0xFF3F3F3F), // Tab container background
        tabContent = Color.White, // Text within tabs,
        secondaryText = Color(0xFFB0B0B0),
    )

fun ColorScheme.toColors(isDarkMode: Boolean): Colors =
    Colors(
        white = if (isDarkMode) Color(0xFF1E1B1B) else Color.White,
        black = if (isDarkMode) Color.White else Color.Black,
        background = this.background,
        toolbarBG = this.onBackground,
        cardBG = this.tertiaryContainer,
        cardContent = this.onTertiaryContainer,
        changeGrowth = this.onError,
        changeDecrease = this.error,
        tabContainer = this.secondaryContainer,
        tabContent = this.onSecondaryContainer,
        secondaryText = this.secondary,
    )

@Composable
fun AAATheme(
    themeType: ThemeType = ThemeType.LIGHT,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    typography: Typography = AAATheme.typography,
    spaces: Spaces = AAATheme.spaces,
    shapes: Shapes = AAATheme.shapes,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    val colorsTheme = when (themeType) {
        ThemeType.AUTO -> when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
                if (isDarkMode) dynamicDarkColorScheme(context).toColors(true)
                else dynamicLightColorScheme(context).toColors(false)
            }

            isDarkMode -> darkColors()
            else -> lightColors()
        }

        ThemeType.DYNAMIC -> @SuppressLint("NewApi") {
            if (isDarkMode) dynamicDarkColorScheme(context).toColors(true)
            else dynamicLightColorScheme(context).toColors(false)
        }

        ThemeType.DARK -> darkColors()
        else -> lightColors()
    }

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

package mega.triple.aaa.presentation.core.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        tabContent = Color.White, // Text within tabs
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
    )

@Composable
fun AAATheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    typography: Typography = AAATheme.typography,
    spaces: Spaces = AAATheme.spaces,
    shapes: Shapes = AAATheme.shapes,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    var selectedMode by remember { mutableIntStateOf(0) }

    val colorsTheme = when (selectedMode) {
        0 -> when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
                if (isDarkMode) dynamicDarkColorScheme(context).toColors(true)
                else dynamicLightColorScheme(context).toColors(false)
            }

            isDarkMode -> darkColors()
            else -> lightColors()
        }

        1 -> @SuppressLint("NewApi") {
            if (isDarkMode) dynamicDarkColorScheme(context).toColors(true)
            else dynamicLightColorScheme(context).toColors(false)
        }

        2 -> darkColors()
        else -> lightColors()
    }

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
        content = {
            Box(
                contentAlignment = Alignment.TopCenter,
            ) {
                content()
                TempThemeChooser(
                    index = selectedMode,
                    onClick = { selectedMode = it },
                )
            }
        },
    )
}

@Composable
fun TempThemeChooser(
    modifier: Modifier = Modifier,
    index: Int,
    onClick: (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = index,
        modifier = modifier
    ) {
        Tab(
            selected = index == 0,
            onClick = { onClick(0) }
        ) {
            Text("Auto")
        }
        Tab(
            selected = index == 1,
            enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
            onClick = { onClick(1) }
        ) {
            Text("Dynamic")
        }
        Tab(
            selected = index == 2,
            onClick = { onClick(2) }
        ) {
            Text("Dark")
        }
        Tab(
            selected = index == 3,
            onClick = { onClick(3) }
        ) {
            Text("Light")
        }
    }
}

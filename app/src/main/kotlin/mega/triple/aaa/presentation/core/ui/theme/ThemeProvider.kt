package mega.triple.aaa.presentation.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import mega.triple.aaa.presentation.core.ui.theme.components.Colors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalColors
import mega.triple.aaa.presentation.core.ui.theme.components.LocalShapes
import mega.triple.aaa.presentation.core.ui.theme.components.LocalSpaces
import mega.triple.aaa.presentation.core.ui.theme.components.LocalTypography
import mega.triple.aaa.presentation.core.ui.theme.components.Shapes
import mega.triple.aaa.presentation.core.ui.theme.components.Spaces
import mega.triple.aaa.presentation.core.ui.theme.components.Typography

object AAATheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
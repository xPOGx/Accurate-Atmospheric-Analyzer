package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spaces(
    val size1: Dp = 1.dp,
    val size2: Dp = 2.dp,
    val size3: Dp = 3.dp,
    val size4: Dp = 4.dp,
    val size6: Dp = 6.dp,
    val size8: Dp = 8.dp,
    val size10: Dp = 10.dp,
    val size12: Dp = 12.dp,
    val size14: Dp = 14.dp,
    val size16: Dp = 16.dp,
    val size18: Dp = 18.dp,
    val size20: Dp = 20.dp,
    val size22: Dp = 22.dp,
    val size24: Dp = 24.dp,
    val size26: Dp = 26.dp,
    val size28: Dp = 28.dp,
    val size30: Dp = 30.dp,
    val size32: Dp = 32.dp,
    val size34: Dp = 34.dp,
    val size36: Dp = 36.dp,
    val size40: Dp = 40.dp,
    val size44: Dp = 44.dp,
    val size48: Dp = 48.dp,
    val size50: Dp = 50.dp,
    val size52: Dp = 52.dp,
    val size54: Dp = 54.dp,
    val size56: Dp = 56.dp,
    val size58: Dp = 58.dp,
    val size60: Dp = 60.dp,
    val size62: Dp = 62.dp,
    val size72: Dp = 72.dp,
    val size80: Dp = 80.dp,
    val size100: Dp = 100.dp,
)

val LocalSpaces = staticCompositionLocalOf { Spaces() }

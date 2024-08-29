package mega.triple.aaa.presentation.core.ui.theme.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Shapes(
    /**
     * Circular Shape with all the corners sized as the 50 percent of the shape size.
     */
    val circleShape: RoundedCornerShape = CircleShape,
) {
    fun roundedCustom(
        topStart: Dp = 0.dp,
        topEnd: Dp = 0.dp,
        bottomEnd: Dp = 0.dp,
        bottomStart: Dp = 0.dp,
    ) = RoundedCornerShape(
        topStart = CornerSize(topStart),
        topEnd = CornerSize(topEnd),
        bottomEnd = CornerSize(bottomEnd),
        bottomStart = CornerSize(bottomStart),
    )

    fun roundedCustom(corner: CornerSize) =
        RoundedCornerShape(corner, corner, corner, corner)

    fun roundedCustom(size: Dp) = RoundedCornerShape(CornerSize(size))
}

val LocalShapes = staticCompositionLocalOf { Shapes() }

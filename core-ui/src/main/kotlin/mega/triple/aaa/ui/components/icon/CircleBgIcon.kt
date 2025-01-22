package mega.triple.aaa.ui.components.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.shapes
import mega.triple.aaa.ui.theme.AAATheme.spaces

@Composable
fun CircleBgIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    size: Dp = spaces.size28,
    iconSize: Dp = spaces.size16,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(colors.white, shapes.circleShape),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(iconSize),
        )
    }
}

package mega.triple.aaa.ui.components.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import mega.triple.aaa.ui.theme.AAATheme
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
    Icon(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = modifier
            .size(size)
            .background(colors.white, shapes.circleShape)
            .padding((size - iconSize) / 2),
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
private fun CircleBgIconPreview() {
    AAATheme {
        CircleBgIcon(
            iconRes = mega.triple.aaa.ui.R.drawable.ic_sun,
        )
    }
}

package mega.triple.aaa.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.strings.R
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.ext.SpacerHeight
import mega.triple.aaa.ui.components.ext.SpacerWidth
import mega.triple.aaa.ui.components.icon.CircleBgIcon
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography
import java.util.Calendar

@Composable
fun ForecastCard(
    modifier: Modifier = Modifier
) {
    Surface(
        color = colors.cardBG,
        contentColor = colors.cardContent,
        modifier = modifier
            .clip(AAATheme.shapes.cardShape),
    ) {
        Column(modifier = Modifier.padding(vertical = spaces.size12)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = spaces.size12)
            ) {
                CircleBgIcon(iconRes = drawable.ic_clock)
                SpacerWidth(spaces.size8)
                Text(
                    text = stringResource(R.string.common_hourly_forecast),
                    style = typography.ps400size14,
                )
            }
            SpacerHeight(spaces.size16)
            LazyRow(
                contentPadding = PaddingValues(horizontal = spaces.size20),
                horizontalArrangement = Arrangement.spacedBy(spaces.size32)
            ) {
                (0 until 24).forEach {
                    item(contentType = "AAAForecastItem") {
                        ForecastCardItem(time = it)
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastCardItem(
    modifier: Modifier = Modifier,
    time: Int,
) {
    val now = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
    val text = if (now == time) stringResource(R.string.common_now) else time.toString()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = text,
            style = typography.gs400size13
        )
        SpacerHeight(spaces.size4)
        Image(
            painter = painterResource(drawable.img_weather_temp),
            contentDescription = null,
            modifier = Modifier.size(spaces.size32)
        )
        SpacerHeight(spaces.size6)
        Text(
            text = "5°", // TODO real impl
            style = typography.gs400size18,
        )
    }
}

@Preview
@Composable
private fun ForecastCardPreview() {
    AAATheme {
        ForecastCard()
    }
}

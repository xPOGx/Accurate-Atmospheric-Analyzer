package mega.triple.aaa.presentation.core.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerH
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerW
import mega.triple.aaa.presentation.core.ui.components.icon.CircleBgIcon
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import java.util.Calendar

@Composable
fun ForecastCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.cardBG,
            contentColor = colors.cardContent,
        ),
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(vertical = spaces.size12)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = spaces.size12)
            ) {
                CircleBgIcon(iconRes = R.drawable.ic_clock)
                SpacerW(spaces.size8)
                Text(
                    text = "Hourly forecast",
                    style = typography.ps400size14,
                )
            }
            SpacerH(spaces.size16)
            LazyRow(
                contentPadding = PaddingValues(horizontal = spaces.size20),
                horizontalArrangement = Arrangement.spacedBy(spaces.size32)
            ) {
                (0 until 24).forEach {
                    item(contentType = "AAAForecastItem") {
                        AAAForecastCardItem(time = it)
                    }
                }
            }
        }
    }
}

@Composable
fun AAAForecastCardItem(
    modifier: Modifier = Modifier,
    time: Int,
) {
    val now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val text = if (now == time) "Now" else time.toString()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = text,
            style = typography.gs400size13
        )
        SpacerH(spaces.size4)
        Image(
            painter = painterResource(R.drawable.img_weather_temp),
            contentDescription = null,
            modifier = Modifier.size(spaces.size32)
        )
        SpacerH(spaces.size6)
        Text(
            text = "5°",
            style = typography.gs400size18,
        )
    }
}

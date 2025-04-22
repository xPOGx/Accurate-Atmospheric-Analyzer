package mega.triple.aaa.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import mega.triple.aaa.common.ext.isToday
import mega.triple.aaa.common.ext.normalized
import mega.triple.aaa.strings.R
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.ext.SpacerHeight
import mega.triple.aaa.ui.components.ext.SpacerWidth
import mega.triple.aaa.ui.components.icon.CircleBgIcon
import mega.triple.aaa.ui.ext.dailyForecastUiModel
import mega.triple.aaa.ui.ext.formatDate
import mega.triple.aaa.ui.ext.formatTemperature
import mega.triple.aaa.ui.ext.getAccuWeatherIconRes
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    data: DailyForecastUiModel,
) {
    val dateTime = data.epochDate.normalized()

    Surface(
        color = colors.cardBG,
        contentColor = colors.cardContent,
        modifier = modifier
            .clip(AAATheme.shapes.cardShape),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(spaces.size14)
        ) {
            Column {
                Text(
                    text = when (dateTime.isToday()) {
                        true -> stringResource(R.string.common_today)
                        false -> formatDate(dateTime)
                    },
                    style = typography.ps400size16,
                )
                SpacerHeight(spaces.size4)
                Text(
                    text = data.day?.iconPhrase ?: STUB_VALUE,
                    style = typography.ps400size16,
                    color = colors.secondaryText,
                )
            }
            Spacer(Modifier.weight(1f))
            Column {
                Text(
                    text = formatTemperature(
                        data.temperature?.maximum?.value,
                        data.temperature?.maximum?.unit,
                    ),
                    style = typography.ps400size16,
                    color = colors.tabContent,
                )
                SpacerHeight(spaces.size4)
                Text(
                    text = formatTemperature(
                        data.temperature?.minimum?.value,
                        data.temperature?.minimum?.unit,
                    ),
                    style = typography.ps400size16,
                    color = colors.tabContent,
                )
            }
            SpacerWidth(spaces.size10)
            VerticalDivider(
                color = colors.tabContent,
                modifier = Modifier
                    .width(spaces.size1)
                    .height(spaces.size50)
            )
            SpacerWidth(spaces.size10)
            Image(
                painter = painterResource(getAccuWeatherIconRes(data.day?.icon)),
                contentDescription = null,
                modifier = Modifier.size(spaces.size54)
            )
            SpacerWidth(spaces.size10)
            CircleBgIcon(
                iconRes = drawable.ic_expand_more,
                size = spaces.size18,
                iconSize = Dp.Unspecified,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

@Preview
@Composable
fun DayCardPreview() = AAATheme {
    DayCard(
        data = dailyForecastUiModel,
    )
}

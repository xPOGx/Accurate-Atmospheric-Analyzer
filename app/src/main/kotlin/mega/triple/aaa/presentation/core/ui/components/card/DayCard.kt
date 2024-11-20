package mega.triple.aaa.presentation.core.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.common.Constants.STUB_VALUE
import mega.triple.aaa.presentation.core.common.formatDate
import mega.triple.aaa.presentation.core.common.formatTemperature
import mega.triple.aaa.presentation.core.common.isToday
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerHeight
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerWidth
import mega.triple.aaa.presentation.core.ui.components.icon.CircleBgIcon
import mega.triple.aaa.presentation.core.ui.ext.getAccuWeatherIconRes
import mega.triple.aaa.presentation.core.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    data: DailyForecastUiModel,
) {
    val dateTime = data.epochDate * 1000

    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.cardBG,
            contentColor = colors.cardContent,
        ),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(spaces.size14)
        ) {
            Column {
                Text(
                    if (dateTime.isToday())
                        stringResource(R.string.common_today) else formatDate(dateTime),
                    style = typography.ps400size16,
                )
                SpacerHeight(spaces.size4)
                Text(
                    data.day?.iconPhrase ?: STUB_VALUE,
                    style = typography.ps400size16,
                    color = colors.secondaryText,
                )
            }
            Spacer(Modifier.weight(1f))
            Column {
                Text(
                    formatTemperature(
                        data.temperature?.maximum?.value,
                        data.temperature?.maximum?.unit,
                    ),
                    style = typography.ps400size16,
                    color = colors.tabContent,
                )
                SpacerHeight(spaces.size4)
                Text(
                    formatTemperature(
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
                iconRes = R.drawable.ic_expand_more,
                size = spaces.size18,
                iconSize = Dp.Unspecified,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

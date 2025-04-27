package mega.triple.aaa.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import mega.triple.aaa.common.ext.diff
import mega.triple.aaa.common.ext.getTimeDiff
import mega.triple.aaa.home.ext.HomeCardType
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.components.card.ForecastCard
import mega.triple.aaa.ui.components.card.ForecastDailyCard
import mega.triple.aaa.ui.components.card.ParameterCard
import mega.triple.aaa.ui.components.card.RainChanceCard
import mega.triple.aaa.ui.components.view.UvIndexView
import mega.triple.aaa.ui.ext.formatProbability
import mega.triple.aaa.ui.ext.formatSimpleTime
import mega.triple.aaa.ui.ext.formatSpeed
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.model.forecast.DayNightUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography
import kotlin.math.absoluteValue
import mega.triple.aaa.ui.R.drawable as drawableRes

@SuppressLint("RestrictedApi")
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    contentType: HomeCardType = HomeCardType.WIND_SPEED,
    dayNight: DayNightUiModel? = null,
    diffDayNight: DayNightUiModel? = null,
    data: DailyForecastUiModel? = null,
    diffData: DailyForecastUiModel? = null,
    forecast: List<DailyForecastUiModel> = emptyList(),
    dayIndex: Int = 0,
    uvCustomVisible: Boolean = false,
) {
    when (contentType) {
        HomeCardType.WIND_SPEED -> {
            val speed = dayNight?.wind?.speed?.value
            val speedUnit = dayNight?.wind?.speed?.unit
            val diff = diff(speed, diffDayNight?.wind?.speed?.value)
            ParameterCard(
                title = stringResource(string.home_wind_speed),
                description = formatSpeed(speed, speedUnit),
                iconRes = drawableRes.ic_air,
                extra = diff?.let { formatSpeed(it.absoluteValue, speedUnit) to (it > 0) },
                modifier = modifier,
            )
        }

        HomeCardType.RAIN_CHANCE -> {
            val diff = diff(
                dayNight?.rainProbability,
                diffDayNight?.rainProbability
            )
            ParameterCard(
                title = stringResource(string.home_rain_chance),
                description = formatProbability(dayNight?.rainProbability),
                iconRes = drawableRes.ic_rainy,
                extra = diff?.let { formatProbability(diff.absoluteValue) to (diff > 0) },
                modifier = modifier,
            )
        }

        HomeCardType.AIR_QUALITY -> {
            ParameterCard(
                title = stringResource(string.home_air_quality),
                description = data?.airQuality ?: STUB_VALUE,
                iconRes = drawableRes.ic_waves,
                extra = null,
                modifier = modifier,
            )
        }

        HomeCardType.UV_INDEX -> {
            val uvIndex = data?.uvIndex
            val diff = diff(uvIndex, diffData?.uvIndex)
            AnimatedContent(
                targetState = uvCustomVisible,
                label = "uvCustomVisible",
                transitionSpec = {
                    (fadeIn() + slideInHorizontally { it })
                        .togetherWith(fadeOut() + slideOutHorizontally { it })
                }
            ) {
                if (it) {
                    UvIndexView(
                        uvIndex = uvIndex?.toFloat() ?: 0f,
                        modifier = modifier,
                    )
                } else {
                    ParameterCard(
                        title = stringResource(string.home_uv_index),
                        description = uvIndex.toString(),
                        iconRes = drawableRes.ic_sun,
                        extra = diff?.let { diff.toString() to (diff > 0) },
                        modifier = modifier,
                    )
                }
            }
        }

        HomeCardType.FORECAST_HOURLY -> {
            // TODO Day forecast card
            Box {
                ForecastCard(
                    modifier = modifier,
                )
                Text("HOURLY")
            }
        }

        HomeCardType.FORECAST_DAILY -> ForecastDailyCard(
            forecast = forecast,
            dayIndex = dayIndex,
            modifier = modifier,
        )

        HomeCardType.FORECAST_RAIN_CHANCE -> {
            RainChanceCard(
                dayChance = dayNight?.rainProbability ?: 0,
                nightChance = diffDayNight?.rainProbability ?: 0,
            )
        }

        HomeCardType.SUN_RISE -> {
            ParameterCard(
                title = stringResource(string.home_sunrise),
                description = formatSimpleTime(data?.sun?.timeRise),
                descriptionTextStyle = typography.gs500size14,
                iconRes = drawableRes.ic_sun,
                extra = getTimeDiff(data?.sun?.epochRise) to null,
                extraModifier = Modifier.padding(bottom = spaces.size12),
                modifier = modifier,
            )
        }

        HomeCardType.SUN_SET -> {
            ParameterCard(
                title = stringResource(string.home_sunset),
                description = formatSimpleTime(data?.sun?.timeSet),
                descriptionTextStyle = typography.gs500size14,
                iconRes = drawableRes.ic_sunset,
                extra = getTimeDiff(data?.sun?.epochSet) to null,
                extraModifier = Modifier.padding(bottom = spaces.size12),
                modifier = modifier,
            )
        }

        HomeCardType.MOON_RISE -> {
            ParameterCard(
                title = stringResource(string.home_moonrise),
                description = formatSimpleTime(data?.moon?.timeRise),
                descriptionTextStyle = typography.gs500size14,
                iconRes = drawableRes.ic_sunrise,
                extra = getTimeDiff(data?.moon?.epochRise) to null,
                extraModifier = Modifier.padding(bottom = spaces.size12),
                modifier = modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview() {
    AAATheme {
        HomeContent(
            contentType = HomeCardType.FORECAST_DAILY,
        )
    }
}

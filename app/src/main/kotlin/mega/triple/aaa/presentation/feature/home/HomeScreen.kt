package mega.triple.aaa.presentation.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.common.Constants.STUB_VALUE
import mega.triple.aaa.presentation.core.common.ForecastFlows
import mega.triple.aaa.presentation.core.common.diff
import mega.triple.aaa.presentation.core.common.formatProbability
import mega.triple.aaa.presentation.core.common.formatSimpleTime
import mega.triple.aaa.presentation.core.common.formatSpeed
import mega.triple.aaa.presentation.core.common.getTimeDiff
import mega.triple.aaa.presentation.core.ui.components.card.DayCard
import mega.triple.aaa.presentation.core.ui.components.card.ForecastCard
import mega.triple.aaa.presentation.core.ui.components.card.ParameterCard
import mega.triple.aaa.presentation.core.ui.components.tab.DayTab
import mega.triple.aaa.presentation.core.ui.components.toolbar.TopAppBar
import mega.triple.aaa.presentation.core.ui.components.view.UvIndexView
import mega.triple.aaa.presentation.core.ui.ext.noRippleClickable
import mega.triple.aaa.presentation.core.ui.model.location.LocationUiModel
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    location: LocationUiModel? = null,
    forecastFlows: ForecastFlows = ForecastFlows(),
    navigateToSearch: (() -> Unit)? = null,
    navigateToSettings: (() -> Unit)? = null,
) {
    val gridState = rememberLazyGridState()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var uvCustomVisible by remember { mutableStateOf(false) }
    val compact by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex != 0 || selectedIndex == 2
        }
    }

    val changeIndex: ((Int) -> Unit) = { selectedIndex = it }
    val listMode = selectedIndex == 2

    val currentData = when (selectedIndex) {
        0 -> forecastFlows.today
        1 -> forecastFlows.tomorrow
        else -> forecastFlows.forecast.firstOrNull()
    }
    val diffData = when (selectedIndex) {
        0 -> forecastFlows.yesterday
        1 -> forecastFlows.today
        else -> null
    }

    val dayNight = if (currentData?.isDay == true) currentData.day else currentData?.night
    val diffDayNight = if (diffData?.isDay == true) diffData.day else diffData?.night

    val line = if (listMode) 1 else 2
    val allLine: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(line) }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                locationName = location?.locationName,
                data = currentData,
                compact = compact,
                selectedIndex = selectedIndex,
                onSelect = changeIndex,
                onSearch = navigateToSearch,
                onSettings = navigateToSettings,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(line),
            state = gridState,
            contentPadding = PaddingValues(vertical = spaces.size12, horizontal = spaces.size16),
            verticalArrangement = Arrangement.spacedBy(spaces.size12),
            horizontalArrangement = Arrangement.spacedBy(spaces.size16),
            modifier = Modifier.padding(innerPadding)
        ) {
            if (listMode) {
                items(items = forecastFlows.forecast) {
                    DayCard(data = it)
                }
            } else {
                item(span = allLine) {
                    DayTab(
                        selectedIndex = selectedIndex,
                        onSelect = changeIndex,
                        modifier = Modifier
                    )
                }
                item(contentType = "AAACardItem") {
                    val speed = dayNight?.wind?.speed?.value
                    val speedUnit = dayNight?.wind?.speed?.unit
                    val diff = diff(speed, diffDayNight?.wind?.speed?.value)
                    ParameterCard(
                        title = stringResource(R.string.home_wind_speed),
                        description = formatSpeed(speed, speedUnit),
                        iconRes = R.drawable.ic_air,
                        extra = diff?.let {
                            formatSpeed(
                                it.absoluteValue,
                                speedUnit
                            ) to (it > 0)
                        },
                    )
                }
                item(contentType = "AAACardItem") {
                    val diff = diff(dayNight?.rainProbability, diffDayNight?.rainProbability)
                    ParameterCard(
                        title = stringResource(R.string.home_rain_chance),
                        description = formatProbability(dayNight?.rainProbability),
                        iconRes = R.drawable.ic_rainy,
                        extra = diff?.let { formatProbability(diff.absoluteValue) to (diff > 0) },
                    )
                }
                item(contentType = "AAACardItem") {
                    ParameterCard(
                        title = stringResource(R.string.home_air_quality),
                        description = currentData?.airQuality ?: STUB_VALUE,
                        iconRes = R.drawable.ic_waves,
                        extra = null,
                    )
                }
                item(contentType = "AAACardItem") {
                    val uvIndex = currentData?.uvIndex
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
                                modifier = Modifier.noRippleClickable {
                                    uvCustomVisible = false
                                }
                            )
                        } else {
                            ParameterCard(
                                title = stringResource(R.string.home_uv_index),
                                description = uvIndex.toString(),
                                iconRes = R.drawable.ic_sun,
                                extra = diff?.let { diff.toString() to (diff > 0) },
                                modifier = Modifier.noRippleClickable { uvCustomVisible = true }
                            )
                        }
                    }
                }
                item(span = allLine) {
                    ForecastCard()
                }
                // TODO Day forecast card
                item(span = allLine) {
                    ForecastCard()
                }
                // TODO Chance of rain card
                item(span = allLine) {
                    ForecastCard()
                }
                item(contentType = "AAACardItem") {
                    ParameterCard(
                        title = stringResource(R.string.home_sunrise),
                        description = formatSimpleTime(currentData?.sun?.timeRise),
                        descriptionTextStyle = typography.gs500size14,
                        iconRes = R.drawable.ic_sun,
                        extra = getTimeDiff(currentData?.sun?.epochRise) to null,
                        extraModifier = Modifier.padding(bottom = spaces.size12)
                    )
                }
                item(contentType = "AAACardItem") {
                    ParameterCard(
                        title = stringResource(R.string.home_sunset),
                        description = formatSimpleTime(currentData?.sun?.timeSet),
                        descriptionTextStyle = typography.gs500size14,
                        iconRes = R.drawable.ic_sunset,
                        extra = getTimeDiff(currentData?.sun?.epochSet) to null,
                        extraModifier = Modifier.padding(bottom = spaces.size12),
                    )
                }
                item(contentType = "AAACardItem") {
                    ParameterCard(
                        title = stringResource(R.string.home_moonrise),
                        description = formatSimpleTime(currentData?.moon?.timeRise),
                        descriptionTextStyle = typography.gs500size14,
                        iconRes = R.drawable.ic_sunrise,
                        extra = getTimeDiff(currentData?.moon?.epochRise) to null,
                        extraModifier = Modifier.padding(bottom = spaces.size12)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AAATheme {
        HomeScreen()
    }
}

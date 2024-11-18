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
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.common.Constants.STUB_VALUE
import mega.triple.aaa.presentation.core.common.ForecastFlows
import mega.triple.aaa.presentation.core.common.diff
import mega.triple.aaa.presentation.core.common.formatProbability
import mega.triple.aaa.presentation.core.common.formatSimpleTime
import mega.triple.aaa.presentation.core.common.formatSpeed
import mega.triple.aaa.presentation.core.common.getTimeDiff
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
) {
    val gridState = rememberLazyGridState()
    val compact by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex != 0
        }
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    val changeIndex: ((Int) -> Unit) = {
        if (it != 2) {
            selectedIndex = it
        }
    }

    var uvCustomVisible by remember { mutableStateOf(false) }

    val currentData = when (selectedIndex) {
        0 -> forecastFlows.today
        else -> forecastFlows.tomorrow
    }
    val diffData = if (selectedIndex == 0) forecastFlows.yesterday else forecastFlows.today

    val dayNight = if (currentData?.isDay == true) currentData.day else currentData?.night
    val diffDayNight = if (diffData?.isDay == true) diffData.day else diffData?.night

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                locationName = location?.locationName,
                data = currentData,
                compact = compact,
                selectedIndex = selectedIndex,
                onSelect = changeIndex,
                onSearch = navigateToSearch
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        val line = 2
        val allLine: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(2) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(line),
            state = gridState,
            contentPadding = PaddingValues(vertical = spaces.size12, horizontal = spaces.size16),
            verticalArrangement = Arrangement.spacedBy(spaces.size12),
            horizontalArrangement = Arrangement.spacedBy(spaces.size16),
            modifier = Modifier.padding(innerPadding)
        ) {
            item(span = allLine) {
                DayTab(
                    selectedIndex = selectedIndex,
                    onSelect = changeIndex,
                    modifier = Modifier
                )
            }
            item(contentType = "AAACardItem") {
                val speedUnit = dayNight?.wind?.speed?.unit
                val diff = diff(dayNight?.wind?.speed?.value, diffDayNight?.wind?.speed?.value)
                ParameterCard(
                    title = "Wind speed",
                    description = formatSpeed(
                        dayNight?.wind?.speed?.value,
                        speedUnit,
                    ),
                    iconRes = R.drawable.ic_air,
                    extra = diff?.let { formatSpeed(it.absoluteValue, speedUnit) to (it > 0) },
                )
            }
            item(contentType = "AAACardItem") {
                val diff = diff(dayNight?.rainProbability, diffDayNight?.rainProbability)
                ParameterCard(
                    title = "Rain chance",
                    description = formatProbability(dayNight?.rainProbability),
                    iconRes = R.drawable.ic_rainy,
                    extra = diff?.let { formatProbability(diff.absoluteValue) to (diff > 0) },
                )
            }
            item(contentType = "AAACardItem") {
                ParameterCard(
                    title = "Air quality",
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
                            modifier = Modifier.noRippleClickable { uvCustomVisible = false }
                        )
                    } else {
                        ParameterCard(
                            title = "UV Index",
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
                    title = "Sunrise",
                    description = formatSimpleTime(currentData?.sun?.timeRise),
                    descriptionTextStyle = typography.gs500size14,
                    iconRes = R.drawable.ic_sunrise,
                    extra = getTimeDiff(currentData?.sun?.epochRise) to null,
                    extraModifier = Modifier.padding(bottom = spaces.size12)
                )
            }
            item(contentType = "AAACardItem") {
                ParameterCard(
                    title = "Sunset",
                    description = formatSimpleTime(currentData?.sun?.timeSet),
                    descriptionTextStyle = typography.gs500size14,
                    iconRes = R.drawable.ic_sunset,
                    extra = getTimeDiff(currentData?.sun?.epochSet) to null,
                    extraModifier = Modifier.padding(bottom = spaces.size12),
                )
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

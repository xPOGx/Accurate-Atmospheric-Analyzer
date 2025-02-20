package mega.triple.aaa.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import mega.triple.aaa.common.ext.diff
import mega.triple.aaa.common.ext.getTimeDiff
import mega.triple.aaa.domain.ext.ForecastFlows
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.card.DayCard
import mega.triple.aaa.ui.components.card.ForecastCard
import mega.triple.aaa.ui.components.card.ParameterCard
import mega.triple.aaa.ui.components.pulltorefresh.PullToRefreshWrapper
import mega.triple.aaa.ui.components.tab.DayTab
import mega.triple.aaa.ui.components.toolbar.TopAppBar
import mega.triple.aaa.ui.components.view.UvIndexView
import mega.triple.aaa.ui.ext.formatProbability
import mega.triple.aaa.ui.ext.formatSimpleTime
import mega.triple.aaa.ui.ext.formatSpeed
import mega.triple.aaa.ui.model.location.LocationUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography
import java.util.Calendar
import kotlin.math.absoluteValue
import kotlin.math.min

enum class HomeCardType {
    WIND_SPEED,
    RAIN_CHANCE,
    AIR_QUALITY,
    UV_INDEX,
    FORECAST_HOURLY,
    FORECAST_DAILY,
    FORECAST_RAIN_CHANCE,
    SUN_RISE,
    SUN_SET,
    MOON_RISE,
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    location: LocationUiModel? = null,
    forecastFlows: ForecastFlows = ForecastFlows(),
    lastUpdateDate: Calendar? = null,
    isRefreshing: Boolean = false,
    onAction: ((HomeAction) -> Unit)? = null,
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
        1 -> forecastFlows.tomorrow
        else -> forecastFlows.today
    }
    val diffData = when (selectedIndex) {
        0 -> forecastFlows.yesterday
        else -> forecastFlows.today
    }

    val dayNight = if (currentData?.isDay == true) currentData.day else currentData?.night
    val diffDayNight = if (diffData?.isDay == true) diffData.day else diffData?.night

    val line = if (listMode) 1 else 2
    val allLine: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(line) }
    val singleSpan: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(1) }

    val state = rememberPullToRefreshState()
    val threshold = PullToRefreshDefaults.PositionalThreshold

    val topPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    var editMode by remember { mutableStateOf(true) }
    var selectedCard: Pair<HomeCardType, Boolean>? by remember { mutableStateOf(null) }
    var cardsMenu: List<Pair<HomeCardType, Boolean>> by remember {
        mutableStateOf(
            listOf(
                HomeCardType.WIND_SPEED to true,
                HomeCardType.RAIN_CHANCE to true,
                HomeCardType.AIR_QUALITY to true,
                HomeCardType.UV_INDEX to true,
                HomeCardType.FORECAST_HOURLY to true,
                HomeCardType.FORECAST_DAILY to true,
                HomeCardType.FORECAST_RAIN_CHANCE to true,
                HomeCardType.SUN_RISE to true,
                HomeCardType.SUN_SET to true,
                HomeCardType.MOON_RISE to true,
            )
        )
    }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            AnimatedVisibility(
                visible = !editMode,
                enter = slideInVertically() + expandVertically(),
                exit = slideOutVertically() + shrinkVertically { topPadding }, // edgeToEdge boiiis -100 social rating
            ) {
                TopAppBar(
                    locationName = location?.locationName,
                    data = currentData,
                    compact = compact,
                    selectedIndex = selectedIndex,
                    isError = forecastFlows.isAllEmpty,
                    onSelect = changeIndex,
                    onSearch = { onAction?.invoke(HomeAction.OnNavigateSearch) },
                    onSettings = { onAction?.invoke(HomeAction.OnNavigateSettings) },
                    onUpdateAll = { onAction?.invoke(HomeAction.UpdateAllData) },
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshWrapper(
            state = state,
            isRefreshing = isRefreshing,
            threshold = threshold,
            lastUpdateDate = lastUpdateDate,
            enabled = !editMode,
            onRefresh = { onAction?.invoke(HomeAction.Refresh) },
            modifier = Modifier.padding(innerPadding),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(line),
                state = gridState,
                contentPadding = PaddingValues(
                    vertical = spaces.size12,
                    horizontal = spaces.size16
                ),
                verticalArrangement = Arrangement.spacedBy(spaces.size12),
                horizontalArrangement = Arrangement.spacedBy(spaces.size16),
                modifier = Modifier.graphicsLayer {
                    translationY = state.distanceFraction * threshold.roundToPx()
                    alpha = 1f - min(0.5f, state.distanceFraction)
                }
            ) {
                if (listMode) {
                    items(items = forecastFlows.forecast) {
                        DayCard(data = it)
                    }
                } else {
                    if (!editMode) {
                        item(key = "DayTab", span = allLine) {
                            DayTab(
                                selectedIndex = selectedIndex,
                                onSelect = changeIndex,
                                modifier = Modifier
                            )
                        }
                    }
                    cardsMenu.filter { it.second }
                        .forEach { entry ->
                        val span = when (entry.first) {
                            HomeCardType.FORECAST_HOURLY,
                            HomeCardType.FORECAST_DAILY,
                            HomeCardType.FORECAST_RAIN_CHANCE -> allLine

                            else -> singleSpan
                        }
                        item(key = entry.first, span = span) {
                            val color = if (selectedCard?.first == entry.first)
                                Color.Green.copy(alpha = 0.5f) else Color.Transparent
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = color,
                                        shape = RoundedCornerShape(spaces.size12),
                                    )
                                    .combinedClickable(
                                        onLongClick = {
                                            editMode = !editMode
                                            uvCustomVisible = false
                                            selectedCard = null
                                        },
                                        onClick = {
                                            if (entry.first == HomeCardType.UV_INDEX && !editMode) {
                                                uvCustomVisible = !uvCustomVisible
                                            } else {
                                                if (entry == selectedCard) {
                                                    selectedCard = null
                                                } else {
                                                    if (selectedCard != null) {
                                                        val temp = cardsMenu.toMutableList()
                                                        val first = temp.indexOf(selectedCard!!)
                                                        val second = temp.indexOf(entry)
                                                        temp[first] = entry
                                                        temp[second] = selectedCard!!
                                                        cardsMenu = temp
                                                        selectedCard = null
                                                    } else {
                                                        selectedCard = entry
                                                    }
                                                }
                                            }
                                        }
                                    )
                            ) {
                                when (entry.first) {
                                    HomeCardType.WIND_SPEED -> {
                                        val speed = dayNight?.wind?.speed?.value
                                        val speedUnit = dayNight?.wind?.speed?.unit
                                        val diff = diff(speed, diffDayNight?.wind?.speed?.value)
                                        ParameterCard(
                                            title = stringResource(string.home_wind_speed),
                                            description = formatSpeed(speed, speedUnit),
                                            iconRes = drawable.ic_air,
                                            extra = diff?.let {
                                                formatSpeed(
                                                    it.absoluteValue,
                                                    speedUnit
                                                ) to (it > 0)
                                            },
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
                                            iconRes = drawable.ic_rainy,
                                            extra = diff?.let { formatProbability(diff.absoluteValue) to (diff > 0) },
                                        )
                                    }

                                    HomeCardType.AIR_QUALITY -> {
                                        ParameterCard(
                                            title = stringResource(string.home_air_quality),
                                            description = currentData?.airQuality ?: STUB_VALUE,
                                            iconRes = drawable.ic_waves,
                                            extra = null,
                                        )
                                    }

                                    HomeCardType.UV_INDEX -> {
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
                                                )
                                            } else {
                                                ParameterCard(
                                                    title = stringResource(string.home_uv_index),
                                                    description = uvIndex.toString(),
                                                    iconRes = drawable.ic_sun,
                                                    extra = diff?.let { diff.toString() to (diff > 0) },
                                                )
                                            }
                                        }
                                    }

                                    HomeCardType.FORECAST_HOURLY -> {
                                        Box {
                                            ForecastCard()
                                            Text("HOURLY")
                                        }
                                    }

                                    HomeCardType.FORECAST_DAILY -> {
                                        // TODO Day forecast card
                                        Box {
                                            ForecastCard()
                                            Text("DAILY")
                                        }
                                    }

                                    HomeCardType.FORECAST_RAIN_CHANCE -> {
                                        // TODO Chance of rain card
                                        Box {
                                            ForecastCard()
                                            Text("RAIN CHANCE")
                                        }
                                    }

                                    HomeCardType.SUN_RISE -> {
                                        ParameterCard(
                                            title = stringResource(string.home_sunrise),
                                            description = formatSimpleTime(currentData?.sun?.timeRise),
                                            descriptionTextStyle = typography.gs500size14,
                                            iconRes = drawable.ic_sun,
                                            extra = getTimeDiff(currentData?.sun?.epochRise) to null,
                                            extraModifier = Modifier.padding(bottom = spaces.size12)
                                        )
                                    }

                                    HomeCardType.SUN_SET -> {
                                        ParameterCard(
                                            title = stringResource(string.home_sunset),
                                            description = formatSimpleTime(currentData?.sun?.timeSet),
                                            descriptionTextStyle = typography.gs500size14,
                                            iconRes = drawable.ic_sunset,
                                            extra = getTimeDiff(currentData?.sun?.epochSet) to null,
                                            extraModifier = Modifier.padding(bottom = spaces.size12),
                                        )
                                    }

                                    HomeCardType.MOON_RISE -> {
                                        ParameterCard(
                                            title = stringResource(string.home_moonrise),
                                            description = formatSimpleTime(currentData?.moon?.timeRise),
                                            descriptionTextStyle = typography.gs500size14,
                                            iconRes = drawable.ic_sunrise,
                                            extra = getTimeDiff(currentData?.moon?.epochRise) to null,
                                            extraModifier = Modifier.padding(bottom = spaces.size12)
                                        )
                                    }
                                }

                                if (editMode && selectedCard == null) {
                                    IconButton(
                                        onClick = {
                                            selectedCard = null
                                            val temp = cardsMenu.toMutableList()
                                            val index = temp.indexOf(entry)
                                            temp[index] = entry.copy(second = false)
                                            cardsMenu = temp
                                        },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .focusable(),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
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

package mega.triple.aaa.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import mega.triple.aaa.domain.ext.ForecastFlows
import mega.triple.aaa.home.components.HomeContent
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.home.ext.HomeCardType
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.card.DayCard
import mega.triple.aaa.ui.components.card.EmptyCard
import mega.triple.aaa.ui.components.pulltorefresh.PullToRefreshWrapper
import mega.triple.aaa.ui.components.tab.DayTab
import mega.triple.aaa.ui.components.toolbar.TopAppBar
import mega.triple.aaa.ui.model.location.LocationUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import java.util.Calendar
import kotlin.math.min

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
    // States
    val gridState = rememberLazyGridState()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val listMode by remember(selectedIndex) { mutableStateOf(selectedIndex == 2) }
    var uvCustomVisible by remember { mutableStateOf(false) }
    var toolbarTabVisible by remember { mutableStateOf(false) }
    val compact by remember(selectedIndex) {
        derivedStateOf {
            toolbarTabVisible = gridState.firstVisibleItemIndex != 0 || listMode
            gridState.firstVisibleItemIndex != 0 || gridState.firstVisibleItemIndex == 0 && !gridState.canScrollForward || listMode
        }
    }
    // Extensions
    val changeIndex: ((Int) -> Unit) = { selectedIndex = it }
    // Cards data
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
    // Grid spans
    val line = if (listMode) 1 else 2
    val allLine: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(line) }
    val singleSpan: LazyGridItemSpanScope.() -> GridItemSpan = { GridItemSpan(1) }
    val editContentSpan: LazyGridItemSpanScope.(Pair<HomeCardType, Boolean>) -> GridItemSpan =
        { item ->
            when (item.first) {
                HomeCardType.FORECAST_HOURLY,
                HomeCardType.FORECAST_DAILY,
                HomeCardType.FORECAST_RAIN_CHANCE -> allLine()

                else -> singleSpan()
            }
        }
    // Pull to refresh
    val state = rememberPullToRefreshState()
    val threshold = PullToRefreshDefaults.PositionalThreshold
    // Edit Cards
    val topPadding = WindowInsets.statusBars.getTop(LocalDensity.current)
    var editMode by remember { mutableStateOf(false) }
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
    val (availableCards, unavailableCards) = cardsMenu.partition { it.second }
    val scope = rememberCoroutineScope()
    LaunchedEffect(cardsMenu) {
        scope.launch {
            gridState.scroll(MutatePriority.PreventUserInput) {
                scrollBy(-Float.MAX_VALUE / 2)
            }
        }
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
                    toolbarTabVisible = toolbarTabVisible,
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
                    horizontal = spaces.size16,
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
                            )
                        }
                    }
                    if (availableCards.isEmpty()) {
                        item(key = "EmptyAvailableCards", span = allLine) {
                            EmptyCard(
                                modifier = Modifier.animateItem(),
                            ) {
                                val temp = cardsMenu.toMutableList()
                                temp[0] = cardsMenu[0].copy(second = true)
                                cardsMenu = temp
                            }
                        }
                    }
                    items(
                        items = availableCards,
                        key = { item -> item.first },
                        span = editContentSpan,
                    ) { entry ->
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
                                                    val first =
                                                        temp.indexOf(selectedCard!!)
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
                                .animateItem(),
                        ) {
                            HomeContent(
                                contentType = entry.first,
                                dayNight = dayNight,
                                diffDayNight = diffDayNight,
                                data = currentData,
                                diffData = diffData,
                                uvCustomVisible = uvCustomVisible,
                            )

                            if (editMode && selectedCard == null) {
                                IconButton(
                                    onClick = {
                                        selectedCard = null
                                        val temp = cardsMenu.toMutableList()
                                        val index = temp.indexOf(entry)
                                        temp[index] = entry.copy(second = false)
                                        cardsMenu = temp
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                    if (editMode) {
                        if (unavailableCards.isNotEmpty()) {
                            item(key = "NotEmptyUnavailableCards", span = allLine) {
                                Image(
                                    painter = painterResource(drawable.ic_divider),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .heightIn(min = spaces.size80)
                                        .animateItem(),
                                )
                            }
                        }
                        items(
                            items = unavailableCards,
                            key = { item -> item.first },
                            span = editContentSpan,
                        ) { entry ->
                            Box(
                                modifier = Modifier.animateItem(),
                            ) {
                                HomeContent(
                                    contentType = entry.first,
                                    dayNight = dayNight,
                                    diffDayNight = diffDayNight,
                                    data = currentData,
                                    diffData = diffData,
                                    uvCustomVisible = uvCustomVisible,
                                )

                                IconButton(
                                    onClick = {
                                        val temp = cardsMenu.toMutableList()
                                        val index = temp.indexOf(entry)
                                        temp[index] = entry.copy(second = true)
                                        cardsMenu = temp
                                    },
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .background(
                                            color = colors.white,
                                            shape = RoundedCornerShape(spaces.size12),
                                        ),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = colors.black,
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

@Preview
@Composable
private fun HomeScreenPreview() {
    AAATheme {
        HomeScreen()
    }
}

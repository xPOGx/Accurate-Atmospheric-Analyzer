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
import mega.triple.aaa.home.ext.MapExtension.partition
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
    cardsWrapper: HomeCardWrapper = HomeCardWrapper(),
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
    val editContentSpan: LazyGridItemSpanScope.(HomeCardType) -> GridItemSpan = {
        when (it) {
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
    var selectedCard: HomeCardType? by remember { mutableStateOf(null) }
    val (availableCards, unavailableCards) = remember(cardsWrapper) {
        cardsWrapper.cards.partition()
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(cardsWrapper) {
        scope.launch {
            gridState.scroll(MutatePriority.PreventUserInput) {
                scrollBy(-Float.MAX_VALUE / 2)
            }
        }
    }
    // Card management
    fun addFirstItem() {
        val temp = cardsWrapper.cards.toMutableMap()
        val key = temp.keys.first()
        temp[key] = !cardsWrapper.cards[key]!!
        onAction?.invoke(HomeAction.UpdateCardsSetup(temp))
    }

    fun onCardClick(item: HomeCardType) {
        if (item == HomeCardType.UV_INDEX && !editMode) {
            uvCustomVisible = !uvCustomVisible
            return
        }
        if (!editMode) return
        if (item == selectedCard) {
            selectedCard = null
            return
        }
        if (selectedCard == null) {
            selectedCard = item
            return
        }
        selectedCard?.let { selected ->
            val temp = mutableMapOf<HomeCardType, Boolean>()
            cardsWrapper.cards.forEach { entry ->
                when (entry.key) {
                    item -> temp[selected] = cardsWrapper.cards[selected] ?: return
                    selected -> temp[item] = cardsWrapper.cards[item] ?: return
                    else -> temp[entry.key] = entry.value
                }
            }
            selectedCard = null
            onAction?.invoke(HomeAction.UpdateCardsSetup(temp))
        }
    }

    fun toggleEditMode() {
        editMode = !editMode
        uvCustomVisible = false
        selectedCard = null
    }

    fun hideCard(item: HomeCardType) {
        val temp = cardsWrapper.cards.toMutableMap()
        temp[item] = false
        onAction?.invoke(HomeAction.UpdateCardsSetup(temp))
    }

    fun showCard(item: HomeCardType) {
        val temp = cardsWrapper.cards.toMutableMap()
        temp[item] = true
        onAction?.invoke(HomeAction.UpdateCardsSetup(temp))
    }
    // UI
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
                    items(
                        items = forecastFlows.forecast,
                    ) {
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
                                onClick = ::addFirstItem,
                            )
                        }
                    }
                    items(
                        items = availableCards,
                        key = { it.toString() },
                        span = editContentSpan,
                    ) { item: HomeCardType ->
                        val color = if (selectedCard == item)
                            Color.Green.copy(alpha = 0.5f) else Color.Transparent
                        Box(
                            modifier = Modifier
                                .background(
                                    color = color,
                                    shape = RoundedCornerShape(spaces.size12),
                                )
                                .combinedClickable(
                                    onLongClick = ::toggleEditMode,
                                    onClick = {
                                        onCardClick(item)
                                    }
                                )
                                .animateItem(),
                        ) {
                            HomeContent(
                                contentType = item,
                                dayNight = dayNight,
                                diffDayNight = diffDayNight,
                                data = currentData,
                                diffData = diffData,
                                uvCustomVisible = uvCustomVisible,
                            )

                            if (editMode && selectedCard == null) {
                                IconButton(
                                    onClick = {
                                        hideCard(item)
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
                    if (editMode && unavailableCards.isNotEmpty()) {
                        item(key = "NotEmptyUnavailableCards", span = allLine) {
                            Image(
                                painter = painterResource(drawable.ic_divider),
                                contentDescription = null,
                                modifier = Modifier
                                    .heightIn(min = spaces.size80)
                                    .animateItem(),
                            )
                        }
                        items(
                            items = unavailableCards,
                            key = { it.toString() },
                            span = editContentSpan,
                        ) { item ->
                            Box(
                                modifier = Modifier.animateItem(),
                            ) {
                                HomeContent(
                                    contentType = item,
                                    dayNight = dayNight,
                                    diffDayNight = diffDayNight,
                                    data = currentData,
                                    diffData = diffData,
                                    uvCustomVisible = uvCustomVisible,
                                )

                                IconButton(
                                    onClick = {
                                        showCard(item)
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

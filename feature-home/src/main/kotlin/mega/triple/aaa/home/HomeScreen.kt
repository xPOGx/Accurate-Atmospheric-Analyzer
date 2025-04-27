package mega.triple.aaa.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import mega.triple.aaa.home.components.HomeContent
import mega.triple.aaa.home.components.HomeToolbar
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.home.ext.HomeCardType
import mega.triple.aaa.home.ext.MapExtension.partition
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.card.DayCard
import mega.triple.aaa.ui.components.card.EmptyCard
import mega.triple.aaa.ui.components.pulltorefresh.PullToRefreshWrapper
import mega.triple.aaa.ui.components.tab.DayTab
import mega.triple.aaa.ui.model.location.LocationUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    location: LocationUiModel? = null,
    uiState: HomeUiState = HomeUiState(),
    onAction: ((HomeAction) -> Unit)? = null,
) {
    // States
    val gridState = rememberLazyGridState()
    val listMode by remember(uiState.selectedTabId) { mutableStateOf(uiState.selectedTabId == 2) }
    var toolbarTabVisible by remember { mutableStateOf(false) }
    val isCompactToolbar by remember(uiState.selectedTabId) {
        derivedStateOf {
            toolbarTabVisible = gridState.firstVisibleItemIndex != 0 || listMode
            gridState.firstVisibleItemIndex != 0 ||
                    gridState.firstVisibleItemIndex == 0 && !gridState.canScrollForward ||
                    listMode
        }
    }
    // Cards data
    val currentData = when (uiState.selectedTabId) {
        1 -> uiState.forecastFlows.tomorrow
        else -> uiState.forecastFlows.today
    }
    val diffData = when (uiState.selectedTabId) {
        0 -> uiState.forecastFlows.yesterday
        else -> uiState.forecastFlows.today
    }
    val dayNight = when (currentData?.isDay) {
        true -> currentData.day
        else -> currentData?.night
    }
    val diffDayNight = when (diffData?.isDay) {
        true -> diffData.day
        else -> diffData?.night
    }
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
    val (availableCards, unavailableCards) = remember(uiState.cardsWrapper) {
        uiState.cardsWrapper.cards.partition()
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(uiState.cardsWrapper) {
        scope.launch {
            gridState.scroll(MutatePriority.PreventUserInput) {
                scrollBy(-Float.MAX_VALUE / 2)
            }
        }
    }
    // UI
    Scaffold(
        containerColor = colors.background,
        topBar = {
            HomeToolbar(
                location = location,
                currentData = currentData,
                forecastFlows = uiState.forecastFlows,
                selectedIndex = uiState.selectedTabId,
                compact = isCompactToolbar,
                editMode = uiState.editMode,
                toolbarTabVisible = toolbarTabVisible,
                onAction = onAction,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshWrapper(
            state = state,
            isRefreshing = uiState.isRefreshing,
            threshold = threshold,
            lastUpdateDate = uiState.lastUpdatedDate,
            enabled = !uiState.editMode,
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
                    items(uiState.forecastFlows.forecast) {
                        DayCard(data = it)
                    }
                    return@LazyVerticalGrid
                }
                if (!uiState.editMode) {
                    item(
                        span = allLine,
                        key = "DayTab",
                        contentType = "DayTab",
                    ) {
                        DayTab(
                            selectedIndex = uiState.selectedTabId,
                            onSelect = { onAction?.invoke(HomeAction.ChangeDay(it)) },
                        )
                    }
                }
                if (availableCards.isEmpty()) {
                    item(
                        span = allLine,
                        key = "EmptyCard",
                        contentType = "EmptyCard",
                    ) {
                        EmptyCard(
                            onClick = { onAction?.invoke(HomeAction.OnAddFirstCardClick) },
                        )
                    }
                }
                items(
                    items = availableCards,
                    span = editContentSpan,
                    key = { it.name },
                    contentType = { "HomeContent" },
                ) { item: HomeCardType ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = when (uiState.selectedCard) {
                                    item -> Color.Green.copy(alpha = 0.5f)
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(spaces.size12),
                            ).clip(AAATheme.shapes.cardShape)
                            .combinedClickable(
                                onLongClick = { onAction?.invoke(HomeAction.ChangeEditMode) },
                                onClick = { onAction?.invoke(HomeAction.OnCardClick(item)) },
                            ).animateItem(),
                    ) {
                        HomeContent(
                            contentType = item,
                            dayNight = dayNight,
                            diffDayNight = diffDayNight,
                            data = currentData,
                            diffData = diffData,
                            uvCustomVisible = uiState.uvCustomVisible,
                        )

                        if (uiState.editMode && uiState.selectedCard == null) {
                            IconButton(
                                onClick = { onAction?.invoke(HomeAction.HideCard(item)) },
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
                if (uiState.editMode && unavailableCards.isNotEmpty()) {
                    item(
                        key = "UnavailableCardsDivider",
                        span = allLine,
                        contentType = "UnavailableCardsDivider",
                    ) {
                        Image(
                            painter = painterResource(drawable.ic_divider),
                            contentDescription = null,
                            modifier = Modifier
                                .height(spaces.size80)
                                .animateItem(),
                        )
                    }
                    items(
                        items = unavailableCards,
                        span = editContentSpan,
                        key = { it.name },
                        contentType = { "HomeContent" },
                    ) { item ->
                        Box(Modifier.animateItem()) {
                            HomeContent(
                                contentType = item,
                                dayNight = dayNight,
                                diffDayNight = diffDayNight,
                                data = currentData,
                                diffData = diffData,
                                uvCustomVisible = uiState.uvCustomVisible,
                            )

                            IconButton(
                                onClick = { onAction?.invoke(HomeAction.AddCard(item)) },
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

@Preview
@Composable
private fun HomeScreenPreview() {
    AAATheme {
        HomeScreen()
    }
}

package mega.triple.aaa.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import mega.triple.aaa.domain.ext.ForecastFlows
import mega.triple.aaa.home.ext.HomeAction
import mega.triple.aaa.ui.components.toolbar.TopAppBar
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.model.location.LocationUiModel

@Composable
fun HomeToolbar(
    modifier: Modifier = Modifier,
    location: LocationUiModel? = null,
    currentData: DailyForecastUiModel? = null,
    forecastFlows: ForecastFlows = ForecastFlows(),
    selectedIndex: Int = 0,
    compact: Boolean = false,
    editMode: Boolean = false,
    toolbarTabVisible: Boolean = false,
    onAction: ((HomeAction) -> Unit)? = null,
) {
    val topPadding = WindowInsets.statusBars.getTop(LocalDensity.current)

    AnimatedVisibility(
        visible = !editMode,
        enter = slideInVertically() + expandVertically(),
        exit = slideOutVertically() + shrinkVertically { topPadding },
    ) {
        TopAppBar(
            locationName = location?.locationName,
            data = currentData,
            compact = compact,
            toolbarTabVisible = toolbarTabVisible,
            selectedIndex = selectedIndex,
            isError = forecastFlows.isAllEmpty,
            onSelect = { onAction?.invoke(HomeAction.ChangeDay(it)) },
            onSearch = { onAction?.invoke(HomeAction.OnNavigateSearch) },
            onSettings = { onAction?.invoke(HomeAction.OnNavigateSettings) },
            onUpdateAll = { onAction?.invoke(HomeAction.UpdateAllData) },
            modifier = modifier,
        )
    }
}

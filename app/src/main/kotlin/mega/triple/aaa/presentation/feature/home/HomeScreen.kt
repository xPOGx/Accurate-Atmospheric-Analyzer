package mega.triple.aaa.presentation.feature.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.R
import mega.triple.aaa.presentation.core.ui.components.card.ForecastCard
import mega.triple.aaa.presentation.core.ui.components.card.ParameterCard
import mega.triple.aaa.presentation.core.ui.components.tab.DayTab
import mega.triple.aaa.presentation.core.ui.components.toolbar.TopAppBar
import mega.triple.aaa.presentation.core.ui.components.view.UvIndexView
import mega.triple.aaa.presentation.core.ui.ext.noRippleClickable
import mega.triple.aaa.presentation.core.ui.model.LocationUiModel
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    location: LocationUiModel? = null,
    navigateToSearch: (() -> Unit)? = null,
) {
    val gridState = rememberLazyGridState()
    val compact by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex != 0
        }
    }

    var selectedIndex by remember { mutableIntStateOf(0) }
    val changeIndex: ((Int) -> Unit) = { selectedIndex = it }

    var uvCustomVisible by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                locationName = location?.locationName,
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
                ParameterCard(
                    title = "Wind speed",
                    description = "12km/h",
                    iconRes = R.drawable.ic_air,
                    extra = "2 km/h" to false,
                )
            }
            item(contentType = "AAACardItem") {
                ParameterCard(
                    title = "Rain chance",
                    description = "24%",
                    iconRes = R.drawable.ic_rainy,
                    extra = "10%" to true,
                )
            }
            item(contentType = "AAACardItem") {
                ParameterCard(
                    title = "Pressure",
                    description = "720 hpa",
                    iconRes = R.drawable.ic_waves,
                    extra = "32 hpa" to true,
                )
            }
            item(contentType = "AAACardItem") {
                AnimatedContent(
                    targetState = uvCustomVisible,
                    label = "uvCustomVisible",
                    transitionSpec = {
                        (fadeIn() + slideInHorizontally { it })
                            .togetherWith(fadeOut() + slideOutHorizontally { it })
                    }
                ) {
                    if (it) {
                        Card(
                            colors = CardDefaults.cardColors().copy(
                                containerColor = colors.cardBG,
                                contentColor = colors.cardContent,
                            ),
                            modifier = Modifier.noRippleClickable { uvCustomVisible = false },
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                UvIndexView(2f)
                            }
                        }
                    } else {
                        ParameterCard(
                            title = "UV Index",
                            description = "2,3",
                            iconRes = R.drawable.ic_sun,
                            extra = "0.3" to false,
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
                    description = "5:24",
                    descriptionTextStyle = typography.gs500size14,
                    iconRes = R.drawable.ic_night,
                    extra = "N ago" to null,
                    extraModifier = Modifier.padding(bottom = spaces.size12)
                )
            }
            item(contentType = "AAACardItem") {
                ParameterCard(
                    title = "Sunset",
                    description = "19:53",
                    descriptionTextStyle = typography.gs500size14,
                    iconRes = R.drawable.ic_night,
                    extra = "in N" to null,
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

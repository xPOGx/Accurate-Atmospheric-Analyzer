package mega.triple.aaa.presentation.feature.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.components.card.LocationCard
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerHeight
import mega.triple.aaa.presentation.core.ui.ext.LocationType
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CITY
import mega.triple.aaa.presentation.core.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.presentation.core.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import mega.triple.aaa.presentation.feature.search.components.LocationChooseCard
import mega.triple.aaa.presentation.feature.search.ext.SearchAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState = SearchUiState(),
    loadList: ((LocationType) -> Unit)? = null,
    onAction: ((SearchAction) -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null,
) {
    var editMode: LocationType? by remember { mutableStateOf(null) }
    val isSaveVisible = uiState.location.city != null && editMode == null

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Location search",
                        style = typography.ps400size22,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateBack?.invoke() },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                modifier = Modifier.displayCutoutPadding(),
            )
        },
        modifier = modifier.background(colors.background),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = spaces.size16)
        ) {
            AnimatedContent(
                targetState = editMode != null,
                label = "editMode",
            ) { isList ->
                if (isList) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LocationCard(
                            title = "Go back",
                            modifier = Modifier.fillMaxWidth(.5f),
                        ) { editMode = null }
                        SpacerHeight(height = spaces.size8)
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(spaces.size8),
                            contentPadding = PaddingValues(bottom = spaces.size16)
                        ) {
                            items(items = uiState.locationList) { (id, title) ->
                                LocationCard(title = title ?: "Empty Name") {
                                    editMode?.let { mode ->
                                        onAction?.invoke(
                                            when (mode) {
                                                CONTINENT -> SearchAction.Continent(id)
                                                COUNTRY -> SearchAction.Country(id)
                                                CITY -> SearchAction.City(id)
                                            }
                                        )
                                    }

                                    editMode = null
                                }
                            }
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(spaces.size8)) {
                        LocationChooseCard(
                            title = "Continent",
                            value = uiState.location.continent?.englishName,
                        ) {
                            editMode = CONTINENT
                            loadList?.invoke(CONTINENT)
                        }
                        uiState.location.continent?.let {
                            LocationChooseCard(
                                title = "Country",
                                value = uiState.location.country?.englishName,
                            ) {
                                editMode = COUNTRY
                                loadList?.invoke(COUNTRY)
                            }
                        }
                        uiState.location.country?.let {
                            LocationChooseCard(
                                title = "City",
                                value = uiState.location.city?.englishName,
                            ) {
                                editMode = CITY
                                loadList?.invoke(CITY)
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = isSaveVisible,
                enter = slideInVertically { 2 * it },
                exit = slideOutVertically { 2 * it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = spaces.size24)
            ) {
                LocationCard(title = "Save Changes") {
                    /* TODO action */
                    onNavigateBack?.invoke()
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    AAATheme {
        SearchScreen()
    }
}

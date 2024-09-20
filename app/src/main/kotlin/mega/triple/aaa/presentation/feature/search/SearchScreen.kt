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
import mega.triple.aaa.presentation.core.ui.ext.render
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import mega.triple.aaa.presentation.feature.search.components.LocationChooseCard
import mega.triple.aaa.presentation.feature.search.ext.SearchAction
import mega.triple.aaa.presentation.feature.search.ext.SearchAction.OnNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState = SearchUiState(),
    forceMode: Boolean = false,
    onAction: ((SearchAction) -> Unit)? = null,
) {
    var editMode: LocationType? by remember { mutableStateOf(null) }

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
                    if (forceMode) {
                        // STUB
                    } else IconButton(onClick = { onAction?.invoke(OnNavigateBack) }) {
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
                        uiState.locationList.render {
                            SpacerHeight(height = spaces.size8)
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(spaces.size8),
                                contentPadding = PaddingValues(bottom = spaces.size16),
                                modifier = Modifier.padding(horizontal = spaces.size16)
                            ) {
                                items(items = it) { (id, title) ->
                                    LocationCard(title = title ?: "Empty Name") {
                                        editMode?.let { mode ->
                                            onAction?.invoke(
                                                when (mode) {
                                                    CONTINENT -> SearchAction.SaveContinent(id)
                                                    COUNTRY -> SearchAction.SaveCountry(id)
                                                    CITY -> SearchAction.SaveCity(id)
                                                }
                                            )
                                        }
                                        editMode = null
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(spaces.size8),
                        modifier = Modifier.padding(horizontal = spaces.size16),
                    ) {
                        LocationChooseCard(
                            title = "Continent",
                            value = uiState.location.continent?.englishName,
                        ) {
                            editMode = CONTINENT
                            onAction?.invoke(SearchAction.LoadLocations(CONTINENT))
                        }
                        uiState.location.continent?.let {
                            LocationChooseCard(
                                title = "Country",
                                value = uiState.location.country?.englishName,
                            ) {
                                editMode = COUNTRY
                                onAction?.invoke(SearchAction.LoadLocations(COUNTRY))
                            }
                        }
                        uiState.location.country?.let {
                            LocationChooseCard(
                                title = "City",
                                value = uiState.location.city?.let {
                                    "${it.englishName} - ${it.englishType}"
                                },
                            ) {
                                editMode = CITY
                                onAction?.invoke(SearchAction.LoadLocations(CITY))
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = uiState.location.city != null && editMode == null,
                enter = slideInVertically { 2 * it },
                exit = slideOutVertically { 2 * it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = spaces.size16)
                    .padding(bottom = spaces.size24),
            ) {
                LocationCard(title = "Save Changes") {
                    onAction?.invoke(SearchAction.SaveAll)
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

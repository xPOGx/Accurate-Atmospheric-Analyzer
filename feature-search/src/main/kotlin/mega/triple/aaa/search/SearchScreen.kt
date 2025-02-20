package mega.triple.aaa.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.search.components.SearchContentList
import mega.triple.aaa.search.components.SearchContentStatic
import mega.triple.aaa.search.components.SearchToolbar
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.components.card.LocationCard
import mega.triple.aaa.ui.components.ext.SpacerHeight
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState = SearchUiState(),
    forceMode: Boolean = false,
    onAction: ((SearchAction) -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            SearchToolbar(
                editMode = uiState.editMode,
                forceMode = forceMode,
                onAction = onAction,
            )
        },
        modifier = modifier.background(colors.background),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            AnimatedContent(
                targetState = uiState.editMode != null,
                label = "editMode",
            ) { isList ->
                if (isList) {
                    Column {
                        SpacerHeight(spaces.size8)
                        SearchContentList(
                            uiListState = uiState.locationList,
                            filteredList = uiState.filteredList,
                            editMode = uiState.editMode,
                            onAction = onAction
                        )
                    }
                } else {
                    SearchContentStatic(
                        location = uiState.location,
                        onAction = onAction,
                    )
                }
            }
            AnimatedVisibility(
                visible = uiState.location.city != null && uiState.editMode == null,
                enter = slideInVertically { 2 * it },
                exit = slideOutVertically { 2 * it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = spaces.size16)
                    .padding(bottom = spaces.size24),
            ) {
                LocationCard(
                    title = stringResource(string.search_save_changes),
                ) { onAction?.invoke(SearchAction.SaveAll) }
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

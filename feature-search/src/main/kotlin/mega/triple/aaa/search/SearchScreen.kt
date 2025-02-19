package mega.triple.aaa.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.search.components.SearchContentList
import mega.triple.aaa.search.components.SearchContentStatic
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.components.card.LocationCard
import mega.triple.aaa.ui.ext.LocationType
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography

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
                        text = stringResource(string.search_title),
                        style = typography.ps400size22,
                    )
                },
                navigationIcon = {
                    if (forceMode) {
                        // STUB
                    } else IconButton(onClick = { onAction?.invoke(SearchAction.OnNavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
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
                    SearchContentList(
                        locationList = uiState.locationList,
                        editMode = editMode,
                        onChangeEditMode = { editMode = it },
                        onAction = onAction
                    )
                } else {
                    SearchContentStatic(
                        location = uiState.location,
                        onChangeEditMode = { editMode = it },
                        onAction = onAction,
                    )
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
                LocationCard(title = stringResource(string.search_save_changes)) {
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

package mega.triple.aaa.search.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.ext.LocationType
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchToolbar(
    modifier: Modifier = Modifier,
    editMode: LocationType? = null,
    forceMode: Boolean = false,
    isSearchActive: Boolean = false,
    currentQuery: String = "",
    onAction: ((SearchAction) -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    CenterAlignedTopAppBar(
        title = {
            AnimatedContent(isSearchActive) {
                if (it) {
                    TextField(
                        value = currentQuery,
                        onValueChange = {
                            onAction?.invoke(SearchAction.ChangeFilterQuery(it))
                        },
                        singleLine = true,
                        keyboardActions = KeyboardActions { focusManager.clearFocus() },
                        modifier = Modifier.focusRequester(focusRequester),
                    )
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                } else {
                    Text(
                        text = stringResource(string.search_title),
                        style = typography.ps400size22,
                    )
                }
            }
        },
        navigationIcon = {
            if (!forceMode) {
                IconButton(onClick = { onAction?.invoke(SearchAction.OnNavigateBack) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            if (editMode != null) {
                IconButton(
                    onClick = {
                        onAction?.invoke(SearchAction.ChangeSearchMode)
                    },
                ) {
                    AnimatedContent(isSearchActive) {
                        if (it) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun SearchToolbarPreview() {
    AAATheme {
        SearchToolbar()
    }
}

package mega.triple.aaa.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.search.ext.getFirstUniqueSeenCharIndex
import mega.triple.aaa.search.ext.getIndexOfCharBasedOnYPosition
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.components.card.LocationCard
import mega.triple.aaa.ui.components.ext.SpacerHeight
import mega.triple.aaa.ui.components.scrollbar.AlphabetScroller
import mega.triple.aaa.ui.components.scrollbar.ScrollingBubble
import mega.triple.aaa.ui.components.scrollbar.alphabetItemSize
import mega.triple.aaa.ui.ext.LocationType
import mega.triple.aaa.ui.ext.LocationType.CITY
import mega.triple.aaa.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.ui.ext.UI
import mega.triple.aaa.ui.ext.UI.Companion.getOrNull
import mega.triple.aaa.ui.ext.render
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.spaces
import kotlin.collections.get

@Composable
fun SearchContentList(
    modifier: Modifier = Modifier,
    locationList: UI<List<Pair<String?, String?>>> = UI.LOADING,
    editMode: LocationType? = CONTINENT,
    onChangeEditMode: ((LocationType?) -> Unit)? = null,
    onAction: ((SearchAction) -> Unit)? = null,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    val alphabetHeightInPixels = remember { with(density) { alphabetItemSize.toPx() } }
    var alphabetRelativeDragYOffset: Float? by remember { mutableStateOf(null) }
    var alphabetDistanceFromTopOfScreen by remember { mutableFloatStateOf(0f) }

    fun onAlphabetListDrag(
        relativeDragYOffset: Float?,
        containerDistance: Float,
    ) {
        alphabetRelativeDragYOffset = relativeDragYOffset
        alphabetDistanceFromTopOfScreen = containerDistance
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        LocationCard(
            title = stringResource(string.search_go_back),
            modifier = Modifier.fillMaxWidth(.5f),
        ) { onChangeEditMode?.invoke(null) }
        locationList.render {
            SpacerHeight(height = spaces.size8)
            BoxWithConstraints {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    LazyColumn(
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(spaces.size8),
                        contentPadding = PaddingValues(bottom = spaces.size16),
                        modifier = Modifier
                            .padding(horizontal = spaces.size16)
                            .weight(1f)
                    ) {
                        items(items = it) { (id, title) ->
                            LocationCard(
                                title = title ?: stringResource(string.search_empty_name)
                            ) {
                                editMode?.let { mode ->
                                    id?.let {
                                        onAction?.invoke(
                                            when (mode) {
                                                CONTINENT -> SearchAction.SaveContinent(id)
                                                COUNTRY -> SearchAction.SaveCountry(id)
                                                CITY -> SearchAction.SaveCity(id)
                                            }
                                        )
                                    }
                                }
                                onChangeEditMode?.invoke(null)
                            }
                        }
                    }
                    val mapOfFirstLetterIndex: Map<Char, Int> = remember(locationList) {
                        locationList.getOrNull().getFirstUniqueSeenCharIndex()
                    }
                    AlphabetScroller(
                        onAlphabetListDrag = { relativeDragYOffset, containerDistanceFromTopOfScreen ->
                            onAlphabetListDrag(
                                relativeDragYOffset,
                                containerDistanceFromTopOfScreen,
                            )
                            coroutineScope.launch {
                                val indexOfChar =
                                    relativeDragYOffset?.getIndexOfCharBasedOnYPosition(
                                        alphabetHeightInPixels,
                                    )
                                mapOfFirstLetterIndex[indexOfChar]?.let {
                                    lazyListState.scrollToItem(it)
                                }
                            }
                        },
                    )
                }
                alphabetRelativeDragYOffset?.let { yOffset ->
                    ScrollingBubble(
                        boxConstraintMaxWidth = this.maxWidth,
                        bubbleOffsetYFloat = yOffset + alphabetDistanceFromTopOfScreen,
                        currAlphabetScrolledOn = yOffset.getIndexOfCharBasedOnYPosition(
                            alphabetHeightInPixels,
                        ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchContentListPreview() {
    AAATheme {
        SearchContentList()
    }
}

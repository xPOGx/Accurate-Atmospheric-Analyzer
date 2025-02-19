package mega.triple.aaa.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.common.ext.Constants.STUB_VALUE
import mega.triple.aaa.search.ext.SearchAction
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.ext.LocationType
import mega.triple.aaa.ui.ext.LocationType.CITY
import mega.triple.aaa.ui.ext.LocationType.CONTINENT
import mega.triple.aaa.ui.ext.LocationType.COUNTRY
import mega.triple.aaa.ui.model.location.LocationUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.spaces

@Composable
fun SearchContentStatic(
    modifier: Modifier = Modifier,
    location: LocationUiModel = LocationUiModel(),
    onChangeEditMode: ((LocationType) -> Unit)? = null,
    onAction: ((SearchAction) -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spaces.size8),
        modifier = modifier.padding(horizontal = spaces.size16),
    ) {
        LocationChooseCard(
            title = stringResource(string.search_continent),
            value = location.continent?.englishName,
        ) {
            onChangeEditMode?.invoke(CONTINENT)
            onAction?.invoke(SearchAction.LoadLocations(CONTINENT))
        }
        location.continent?.let {
            LocationChooseCard(
                title = stringResource(string.search_country),
                value = location.country?.englishName,
            ) {
                onChangeEditMode?.invoke(COUNTRY)
                onAction?.invoke(SearchAction.LoadLocations(COUNTRY))
            }
        }
        location.country?.let {
            LocationChooseCard(
                title = stringResource(string.search_city),
                value = location.city?.let {
                    stringResource(
                        string.search_item_title,
                        it.englishName ?: STUB_VALUE,
                        it.englishType ?: STUB_VALUE,
                    )
                },
            ) {
                onChangeEditMode?.invoke(CITY)
                onAction?.invoke(SearchAction.LoadLocations(CITY))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchContentStaticPreview() {
    AAATheme {
        SearchContentStatic()
    }
}

package mega.triple.aaa.presentation.feature.search.ext

import mega.triple.aaa.presentation.core.ui.ext.LocationType

sealed class SearchAction {
    data class SaveContinent(val continentId: String) : SearchAction()
    data class SaveCountry(val countryId: String) : SearchAction()
    data class SaveCity(val cityId: String) : SearchAction()
    data object SaveAll : SearchAction()
    data class LoadLocations(val type: LocationType) : SearchAction()
    data object OnNavigateBack : SearchAction()
}
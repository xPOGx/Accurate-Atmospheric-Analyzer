package mega.triple.aaa.search.ext

import mega.triple.aaa.ui.ext.LocationType

sealed class SearchAction {
    data class SaveContinent(val continentId: String) : SearchAction()
    data class SaveCountry(val countryId: String) : SearchAction()
    data class SaveCity(val cityId: String) : SearchAction()
    data object SaveAll : SearchAction()
    data class LoadLocations(val type: LocationType) : SearchAction()
    data object OnNavigateBack : SearchAction()
}
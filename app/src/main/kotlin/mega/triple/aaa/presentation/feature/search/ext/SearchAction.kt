package mega.triple.aaa.presentation.feature.search.ext

sealed class SearchAction {
    data class Continent(val continentId: String) : SearchAction()
    data class Country(val countryId: String) : SearchAction()
    data class City(val cityId: String) : SearchAction()
}
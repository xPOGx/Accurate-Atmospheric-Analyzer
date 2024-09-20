package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.presentation.core.common.Constants.LOCATION_SEPARATOR

data class LocationUiModel(
    val continent: ContinentUiModel? = null,
    val country: CountryUiModel? = null,
    val city: CityUiModel? = null,
) {
    // TODO: from locale
    val locationName: String? by lazy {
        buildString {
            append(city?.englishName)
            append(LOCATION_SEPARATOR)
            append(country?.englishName)
        }
    }
}
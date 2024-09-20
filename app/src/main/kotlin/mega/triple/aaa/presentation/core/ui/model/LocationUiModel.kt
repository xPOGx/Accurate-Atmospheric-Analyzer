package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.presentation.core.common.Constants

data class LocationUiModel(
    val continent: ContinentUiModel? = null,
    val country: CountryUiModel? = null,
    val city: CityUiModel? = null,
) {
    // TODO: from locale
    val locationName: String? by lazy {
        if (isValid()) {
            buildString {
                append(city!!.englishType)
                append(Constants.SPACE)
                append(city.englishName)
                append(Constants.LOCATION_SEPARATOR)
                append(country!!.englishName)
            }
        } else {
            null
        }
    }

    fun isValid() = continent != null && country != null && city != null
}
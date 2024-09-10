package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.presentation.core.common.Constants.LOCATION_SEPARATOR

data class LocationUiModel(
    val continent: String? = null,
    val country: String? = null,
    val city: String? = null,
) {
    val locationName: String? by lazy {
        buildString {
            append(city)
            append(LOCATION_SEPARATOR)
            append(country)
        }
    }
}
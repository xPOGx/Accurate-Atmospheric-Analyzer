package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.domain.location.model.CityDomainModel

data class CityUiModel(
    val id: String?,
    val locationKey: String?,
    val countryID: String?,
    val englishName: String?,
    val englishType: String?,
    val level: Int?,
    val localizedName: String?,
    val localizedType: String?,
) {
    companion object {
        fun CityDomainModel.toUiModel(): CityUiModel =
            CityUiModel(
                id = id,
                locationKey = locationKey,
                countryID = countryID,
                englishName = englishName,
                englishType = englishType,
                level = level,
                localizedName = localizedName,
                localizedType = localizedType,
            )
    }
}
package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.domain.location.model.CountryDomainModel

data class CountryUiModel(
    val id: String,
    val continentId: String,
    val englishName: String?,
    val localizedName: String?,
) {
    companion object {
        fun CountryDomainModel.toUiModel(): CountryUiModel =
            CountryUiModel(
                id = id,
                continentId = continentId,
                englishName = englishName,
                localizedName = localizedName,
            )
    }
}
package mega.triple.aaa.presentation.core.ui.model

import mega.triple.aaa.domain.location.model.ContinentDomainModel

data class ContinentUiModel(
    val id: String,
    val englishName: String?,
    val localizedName: String?,
) {
    companion object {
        fun ContinentDomainModel.toUiModel(): ContinentUiModel =
            ContinentUiModel(
                id = id,
                englishName = englishName,
                localizedName = localizedName,
            )
    }
}
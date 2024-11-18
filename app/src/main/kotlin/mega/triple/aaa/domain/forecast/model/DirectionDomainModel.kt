package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.DirectionDbModel
import mega.triple.aaa.data.network.response.model.DirectionResponse
import mega.triple.aaa.presentation.core.ui.model.forecast.DirectionUiModel

data class DirectionDomainModel(
    val degrees: Int?,
    val english: String?,
    val localized: String?
) {
    companion object {
        fun DirectionResponse.toDbModel(): DirectionDbModel =
            DirectionDbModel(
                degrees = degrees,
                english = english,
                localized = localized,
            )

        fun DirectionResponse.toDomainModel(): DirectionDomainModel =
            DirectionDomainModel(
                degrees = degrees,
                english = english,
                localized = localized,
            )

        fun DirectionDbModel.toDomainModel(): DirectionDomainModel =
            DirectionDomainModel(
                degrees = degrees,
                english = english,
                localized = localized,
            )

        fun DirectionDomainModel.toUiModel(): DirectionUiModel =
            DirectionUiModel(
                degrees = degrees,
                english = english,
                localized = localized,
            )
    }
}

package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.ValueDbModel
import mega.triple.aaa.data.network.response.model.ValueResponse
import mega.triple.aaa.presentation.core.ui.model.forecast.ValueUiModel

data class ValueDomainModel(
    val phrase: String?,
    val unit: String?,
    val unitType: Int?,
    val value: Double?
) {
    companion object {
        fun ValueResponse.toDbModel(): ValueDbModel =
            ValueDbModel(
                phrase = phrase,
                unit = unit,
                unitType = unitType,
                value = value,
            )

        fun ValueResponse.toDomainModel(): ValueDomainModel =
            ValueDomainModel(
                phrase = phrase,
                unit = unit,
                unitType = unitType,
                value = value,
            )

        fun ValueDbModel.toDomainModel(): ValueDomainModel =
            ValueDomainModel(
                phrase = phrase,
                unit = unit,
                unitType = unitType,
                value = value,
            )

        fun ValueDomainModel.toUiModel(): ValueUiModel =
            ValueUiModel(
                phrase = phrase,
                unit = unit,
                unitType = unitType,
                value = value,
            )
    }
}

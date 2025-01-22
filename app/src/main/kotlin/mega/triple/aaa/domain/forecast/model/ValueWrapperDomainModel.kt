package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toUiModel
import mega.triple.aaa.local.model.forecast.ValueWrapperDbModel
import mega.triple.aaa.network.response.model.ValueWrapper
import mega.triple.aaa.presentation.core.ui.model.forecast.ValueWrapperUiModel

data class ValueWrapperDomainModel(
    val average: ValueDomainModel?,
    val maximum: ValueDomainModel?,
    val minimum: ValueDomainModel?
) {
    companion object {
        fun ValueWrapper.toDbModel(): ValueWrapperDbModel =
            ValueWrapperDbModel(
                average = average?.toDbModel(),
                maximum = maximum?.toDbModel(),
                minimum = minimum?.toDbModel(),
            )

        fun ValueWrapper.toDomainModel(): ValueWrapperDomainModel =
            ValueWrapperDomainModel(
                average = average?.toDomainModel(),
                maximum = maximum?.toDomainModel(),
                minimum = minimum?.toDomainModel(),
            )

        fun ValueWrapperDbModel.toDomainModel(): ValueWrapperDomainModel =
            ValueWrapperDomainModel(
                average = average?.toDomainModel(),
                maximum = maximum?.toDomainModel(),
                minimum = minimum?.toDomainModel(),
            )

        fun ValueWrapperDomainModel.toUiModel(): ValueWrapperUiModel =
            ValueWrapperUiModel(
                average = average?.toUiModel(),
                maximum = maximum?.toUiModel(),
                minimum = minimum?.toUiModel(),
            )
    }
}

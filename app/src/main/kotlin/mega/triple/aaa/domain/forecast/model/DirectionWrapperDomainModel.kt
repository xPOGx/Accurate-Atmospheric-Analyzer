package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.DirectionWrapperDbModel
import mega.triple.aaa.data.network.response.model.DirectionWrapper
import mega.triple.aaa.domain.forecast.model.DirectionDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.DirectionDomainModel.Companion.toDomainModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.forecast.model.ValueDomainModel.Companion.toDomainModel


data class DirectionWrapperDomainModel(
    val direction: DirectionDomainModel?,
    val speed: ValueDomainModel?
) {
    companion object {
        fun DirectionWrapper.toDbModel(): DirectionWrapperDbModel =
            DirectionWrapperDbModel(
                direction = direction?.toDbModel(),
                speed = speed?.toDbModel(),
            )

        fun DirectionWrapper.toDomainModel(): DirectionWrapperDomainModel =
            DirectionWrapperDomainModel(
                direction = direction?.toDomainModel(),
                speed = speed?.toDomainModel(),
            )

        fun DirectionWrapperDbModel.toDomainModel(): DirectionWrapperDomainModel =
            DirectionWrapperDomainModel(
                direction = direction?.toDomainModel(),
                speed = speed?.toDomainModel(),
            )
    }
}

package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.local.model.forecast.RelativeHumidityDbModel
import mega.triple.aaa.network.response.model.RelativeHumidityResponse
import mega.triple.aaa.ui.model.forecast.RelativeHumidityUiModel

data class RelativeHumidityDomainModel(
    val average: Int?,
    val maximum: Int?,
    val minimum: Int?
) {
    companion object {
        fun RelativeHumidityResponse.toDbModel(): RelativeHumidityDbModel =
            RelativeHumidityDbModel(
                average = average,
                maximum = maximum,
                minimum = minimum,
            )

        fun RelativeHumidityResponse.toDomainModel(): RelativeHumidityDomainModel =
            RelativeHumidityDomainModel(
                average = average,
                maximum = maximum,
                minimum = minimum,
            )

        fun RelativeHumidityDbModel.toDomainModel(): RelativeHumidityDomainModel =
            RelativeHumidityDomainModel(
                average = average,
                maximum = maximum,
                minimum = minimum,
            )

        fun RelativeHumidityDomainModel.toUiModel(): RelativeHumidityUiModel =
            RelativeHumidityUiModel(
                average = average,
                maximum = maximum,
                minimum = minimum,
            )
    }
}

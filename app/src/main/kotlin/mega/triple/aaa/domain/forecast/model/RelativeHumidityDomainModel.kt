package mega.triple.aaa.domain.forecast.model

import mega.triple.aaa.data.local.model.forecast.RelativeHumidityDbModel
import mega.triple.aaa.data.network.response.model.RelativeHumidityResponse

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
    }
}

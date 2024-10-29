package mega.triple.aaa.data.network.response.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelativeHumidityResponse(
    @SerialName("Average")
    val average: Int? = null,
    @SerialName("Maximum")
    val maximum: Int?,
    @SerialName("Minimum")
    val minimum: Int?
)
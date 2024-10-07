package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueWrapper(
    @SerialName("Average")
    val average: ValueResponse?,
    @SerialName("Maximum")
    val maximum: ValueResponse?,
    @SerialName("Minimum")
    val minimum: ValueResponse?
)
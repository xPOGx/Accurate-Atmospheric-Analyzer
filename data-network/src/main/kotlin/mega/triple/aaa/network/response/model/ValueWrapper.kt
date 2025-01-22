package mega.triple.aaa.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueWrapper(
    @SerialName("Average")
    val average: ValueResponse? = null,
    @SerialName("Maximum")
    val maximum: ValueResponse?,
    @SerialName("Minimum")
    val minimum: ValueResponse?
)
package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectionWrapper(
    @SerialName("Direction")
    val direction: DirectionResponse?,
    @SerialName("Speed")
    val speed: ValueResponse?
)
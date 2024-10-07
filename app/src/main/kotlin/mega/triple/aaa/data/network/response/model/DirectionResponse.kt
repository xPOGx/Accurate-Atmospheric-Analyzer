package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DirectionResponse(
    @SerialName("Degrees")
    val degrees: Int?,
    @SerialName("English")
    val english: String?,
    @SerialName("Localized")
    val localized: String?
)
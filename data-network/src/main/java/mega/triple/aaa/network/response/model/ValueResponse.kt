package mega.triple.aaa.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueResponse(
    @SerialName("Phrase")
    val phrase: String? = null,
    @SerialName("Unit")
    val unit: String?,
    @SerialName("UnitType")
    val unitType: Int?,
    @SerialName("Value")
    val value: Double?
)
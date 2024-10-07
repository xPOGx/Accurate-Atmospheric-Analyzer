package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueResponse(
    @SerialName("Phrase")
    val phrase: String?,
    @SerialName("Unit")
    val unit: String?,
    @SerialName("UnitType")
    val unitType: Int?,
    @SerialName("Value")
    val value: Double?
)
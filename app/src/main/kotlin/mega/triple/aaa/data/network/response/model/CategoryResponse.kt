package mega.triple.aaa.data.network.response.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("Category")
    val category: String?,
    @SerialName("CategoryValue")
    val categoryValue: Int?,
    @SerialName("Name")
    val name: String?,
    @SerialName("Type")
    val type: String?,
    @SerialName("Value")
    val value: Int?
)
package mega.triple.aaa.local.model.forecast

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDbModel(
    @ColumnInfo("category")
    val category: String?,
    @ColumnInfo("category_value")
    val categoryValue: Int?,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("type")
    val type: String?,
    @ColumnInfo("value")
    val value: Int?,
)
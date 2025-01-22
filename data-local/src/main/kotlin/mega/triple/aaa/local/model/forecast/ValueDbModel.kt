package mega.triple.aaa.local.model.forecast

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class ValueDbModel(
    @ColumnInfo("phrase")
    val phrase: String?,
    @ColumnInfo("unit")
    val unit: String?,
    @ColumnInfo("unit_type")
    val unitType: Int?,
    @ColumnInfo("value")
    val value: Double?
)

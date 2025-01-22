package mega.triple.aaa.local.model.forecast

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class RelativeHumidityDbModel(
    @ColumnInfo("Average")
    val average: Int?,
    @ColumnInfo("Maximum")
    val maximum: Int?,
    @ColumnInfo("Minimum")
    val minimum: Int?
)
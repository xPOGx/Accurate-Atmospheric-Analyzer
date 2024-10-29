package mega.triple.aaa.data.local.model.forecast

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class DirectionDbModel(
    @ColumnInfo("degrees")
    val degrees: Int?,
    @ColumnInfo("english")
    val english: String?,
    @ColumnInfo("localized")
    val localized: String?
)

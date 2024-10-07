package mega.triple.aaa.data.local.model.forecast

import androidx.room.ColumnInfo

data class SunMoonDbModel(
    @ColumnInfo("age")
    val age: Int?,
    @ColumnInfo("epoch_rise")
    val epochRise: Long?,
    @ColumnInfo("epoch_set")
    val epochSet: Long?,
    @ColumnInfo("phase")
    val phase: String?,
    @ColumnInfo("rise")
    val timeRise: String?,
    @ColumnInfo("set")
    val timeSet: String?
)

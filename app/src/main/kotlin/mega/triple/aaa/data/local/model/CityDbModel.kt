package mega.triple.aaa.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cities",
)
data class CityDbModel(
    @PrimaryKey
    @ColumnInfo("db_id")
    val dbId: String,
    @ColumnInfo("id")
    val id: String?,
    @ColumnInfo("location_key")
    val locationKey: String?,
    @ColumnInfo("country_id")
    val countryID: String?,
    @ColumnInfo("english_name")
    val englishName: String?,
    @ColumnInfo("english_type")
    val englishType: String?,
    @ColumnInfo("level")
    val level: Int?,
    @ColumnInfo("localized_name")
    val localizedName: String?,
    @ColumnInfo("localized_type")
    val localizedType: String?,
)
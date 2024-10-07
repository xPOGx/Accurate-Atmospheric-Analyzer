package mega.triple.aaa.data.local.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "countries",
)
data class CountryDbModel(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("continent_id")
    val continentId: String,
    @ColumnInfo("english_name")
    val englishName: String?,
    @ColumnInfo("localized_name")
    val localizedName: String?,
)
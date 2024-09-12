package mega.triple.aaa.data.local.model

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
    @ColumnInfo("english_name")
    val englishName: String?,
    @ColumnInfo("continent_id")
    val continentId: String?,
    @ColumnInfo("localized_name")
    val localizedName: String?,
)
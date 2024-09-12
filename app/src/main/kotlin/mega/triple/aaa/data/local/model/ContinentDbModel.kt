package mega.triple.aaa.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "continents",
)
data class ContinentDbModel(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("english_name")
    val englishName: String?,
    @ColumnInfo("localized_name")
    val localizedName: String?,
)
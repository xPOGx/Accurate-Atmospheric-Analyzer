package mega.triple.aaa.data.local.dao.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.location.CityDbModel

@Dao
interface CityDao {
    @Query("SELECT * FROM cities WHERE country_id = :countryId")
    fun getCities(countryId: String): Flow<List<CityDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(list: List<CityDbModel>)
}
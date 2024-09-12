package mega.triple.aaa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.CountryDbModel

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries WHERE continent_id = :continentId")
    fun getCountries(continentId: String): Flow<List<CountryDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(list: List<CountryDbModel>)
}
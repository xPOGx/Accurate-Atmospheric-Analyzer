package mega.triple.aaa.local.dao.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.local.model.location.ContinentDbModel

@Dao
interface ContinentDao {
    @Query("SELECT * FROM continents")
    fun getContinents(): Flow<List<ContinentDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContinents(list: List<ContinentDbModel>)
}
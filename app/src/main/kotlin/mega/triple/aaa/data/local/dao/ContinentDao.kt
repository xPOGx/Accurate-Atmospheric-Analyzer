package mega.triple.aaa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.ContinentDbModel

@Dao
interface ContinentDao {
    @Query("SELECT * FROM continents")
    fun getContinents(): Flow<List<ContinentDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContinents(list: List<ContinentDbModel>)
}
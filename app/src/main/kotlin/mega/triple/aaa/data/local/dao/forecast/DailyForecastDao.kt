package mega.triple.aaa.data.local.dao.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.data.local.model.forecast.daily.DailyForecastDbModel

@Dao
interface DailyForecastDao {
    @Query("SELECT * FROM daily_forecast WHERE epoch_date >= :time ORDER BY epoch_date ASC")
    fun getDailyForecast(time: Long): Flow<List<DailyForecastDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>)
}
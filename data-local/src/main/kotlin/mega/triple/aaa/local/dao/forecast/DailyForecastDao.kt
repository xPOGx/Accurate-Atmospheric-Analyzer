package mega.triple.aaa.local.dao.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.local.model.forecast.daily.DailyForecastDbModel

@Dao
interface DailyForecastDao {
    @Query(
        "SELECT * " +
        "FROM daily_forecast " +
        "WHERE epoch_date >= CAST(strftime('%s', 'now', 'utc', 'start of day') AS INTEGER) " +
        "ORDER BY epoch_date ASC"
    )
    fun getDailyForecast(): Flow<List<DailyForecastDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecasts(list: List<DailyForecastDbModel>)

    @Query(
        "SELECT * " +
        "FROM DAILY_FORECAST " +
        "WHERE epoch_date >= CAST(strftime('%s', 'now', 'utc', 'start of day') AS INTEGER) " +
        "AND epoch_date < CAST(strftime('%s', 'now', 'utc', 'start of day', '+1 day') AS INTEGER)"
    )
    fun getTodayForecast(): Flow<DailyForecastDbModel?>

    @Query(
        "SELECT * " +
        "FROM DAILY_FORECAST " +
        "WHERE epoch_date >= CAST(strftime('%s', 'now', 'utc', 'start of day', '+1 day') AS INTEGER) " +
        "AND epoch_date < CAST(strftime('%s', 'now', 'utc', 'start of day', '+2 day') AS INTEGER)"
    )
    fun getTomorrowForecast(): Flow<DailyForecastDbModel?>

    @Query(
        "SELECT * " +
        "FROM DAILY_FORECAST " +
        "WHERE epoch_date >= CAST(strftime('%s', 'now', 'utc', 'start of day', '-1 day') AS INTEGER) " +
        "AND epoch_date < CAST(strftime('%s', 'now', 'utc', 'start of day') AS INTEGER)"
    )
    fun getYesterdayForecast(): Flow<DailyForecastDbModel?>
}

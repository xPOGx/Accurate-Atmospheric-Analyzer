package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyLocationKey
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.settings.SetLastUpdateUC
import mega.triple.aaa.local.source.ForecastDbSource
import mega.triple.aaa.network.source.ForecastNetSource

class UpdateDailyForecastUCImpl(
    private val dbSource: ForecastDbSource,
    private val netSource: ForecastNetSource,
    private val locationUC: GetLocationUC,
    private val setLastUpdateUC: SetLastUpdateUC,
) : UpdateDailyForecastUC {
    override suspend operator fun invoke(): Result<Unit> {
        val locationKey = locationUC().first()?.city?.locationKey
            ?: return Result.failure(EmptyLocationKey())

        return try {
            netSource.get5dayForecast(locationKey = locationKey)
                .mapCatching { wrapper ->
                    wrapper.dailyForecasts?.map { it.toDbModel() } ?: throw NullResult()
                }.onSuccess {
                    dbSource.insertDailyForecasts(it)
                    setLastUpdateUC(System.currentTimeMillis())
                }.onFailure {
                    throw it
                }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

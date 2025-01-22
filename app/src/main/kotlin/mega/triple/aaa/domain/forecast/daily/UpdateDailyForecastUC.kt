package mega.triple.aaa.domain.forecast.daily

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyLocationKey
import mega.triple.aaa.domain.ext.ForecastHelper
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.local.source.ForecastDbSource
import mega.triple.aaa.network.source.ForecastNetSource
import javax.inject.Inject

class UpdateDailyForecastUC @Inject constructor(
    private val dbSource: ForecastDbSource,
    private val netSource: ForecastNetSource,
    private val locationUC: GetLocationUC,
    private val forecastHelper: ForecastHelper,
) {
    suspend operator fun invoke(): Result<Unit> {
        val locationKey = locationUC().first()?.city?.locationKey
            ?: return Result.failure(EmptyLocationKey())

        return try {
            netSource.get5dayForecast(locationKey = locationKey)
                .mapCatching { wrapper ->
                    wrapper.dailyForecasts?.map { it.toDbModel() } ?: throw NullResult()
                }.onSuccess {
                    dbSource.insertDailyForecasts(it)
                    forecastHelper.initFlows()
                }.onFailure {
                    throw it
                }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

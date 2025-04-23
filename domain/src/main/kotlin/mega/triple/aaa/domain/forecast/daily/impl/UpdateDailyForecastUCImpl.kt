package mega.triple.aaa.domain.forecast.daily.impl

import kotlinx.coroutines.flow.first
import mega.triple.aaa.domain.ext.EmptyLocationKey
import mega.triple.aaa.domain.ext.NullResult
import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.forecast.daily.UpdateDailyForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toDbModel
import mega.triple.aaa.domain.location.GetLocationUC
import mega.triple.aaa.domain.settings.SetLastUpdateUC
import mega.triple.aaa.local.source.ForecastDbSource
import mega.triple.aaa.network.source.ForecastNetSource
import java.util.Calendar

class UpdateDailyForecastUCImpl(
    private val dbSource: ForecastDbSource,
    private val netSource: ForecastNetSource,
    private val locationUC: GetLocationUC,
    private val setLastUpdateUC: SetLastUpdateUC,
) : UpdateDailyForecastUC {
    override suspend operator fun invoke(): Result<Unit> =
        resultLauncher {
            val locationKey = locationUC().first()?.city?.locationKey ?: throw EmptyLocationKey()

            netSource.get5dayForecast(locationKey = locationKey)
                .mapCatching { wrapper ->
                    wrapper.dailyForecasts?.map { it.toDbModel() } ?: throw NullResult()
                }.onSuccess {
                    dbSource.insertDailyForecasts(it)
                    setLastUpdateUC(Calendar.getInstance().timeInMillis)
                }.onFailure {
                    throw it
                }
        }
}

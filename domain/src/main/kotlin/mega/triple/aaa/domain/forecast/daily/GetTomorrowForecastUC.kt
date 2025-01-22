package mega.triple.aaa.domain.forecast.daily

import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel

interface GetTomorrowForecastUC {
    suspend operator fun invoke(): Result<DailyForecastDomainModel>
}

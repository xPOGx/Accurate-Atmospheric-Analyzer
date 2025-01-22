package mega.triple.aaa.domain.forecast.daily

import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel

interface Get5DayForecastUC {
    suspend operator fun invoke(): Result<List<DailyForecastDomainModel>>
}

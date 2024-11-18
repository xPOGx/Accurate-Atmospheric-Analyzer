package mega.triple.aaa.presentation.core.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mega.triple.aaa.domain.forecast.daily.Get5DayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTodayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.daily.GetYesterdayForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toUiModel
import mega.triple.aaa.presentation.core.ui.model.forecast.DailyForecastUiModel
import javax.inject.Inject

class ForecastHelper @Inject constructor(
    private val getYesterdayForecastUC: GetYesterdayForecastUC,
    private val getTodayForecastUC: GetTodayForecastUC,
    private val getTomorrowForecastUC: GetTomorrowForecastUC,
    private val get5DayForecastUC: Get5DayForecastUC,
) {
    private val _forecastFlows = MutableStateFlow<ForecastFlows>(ForecastFlows())
    val forecastFlows = _forecastFlows.asStateFlow()

    suspend fun initFlows() {
        val yesterday = getYesterdayForecastUC().getOrNull()?.toUiModel()
        val today = getTodayForecastUC().getOrNull()?.toUiModel()
        val tomorrow = getTomorrowForecastUC().getOrNull()?.toUiModel()
        val forecast = get5DayForecastUC().getOrNull()?.map { it.toUiModel() } ?: emptyList()

        _forecastFlows.update {
            it.copy(
                yesterday = yesterday,
                today = today,
                tomorrow = tomorrow,
                forecast = forecast,
            )
        }
    }
}

data class ForecastFlows(
    val yesterday: DailyForecastUiModel? = null,
    val today: DailyForecastUiModel? = null,
    val tomorrow: DailyForecastUiModel? = null,
    val forecast: List<DailyForecastUiModel> = emptyList(),
)

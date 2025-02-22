package mega.triple.aaa.widget

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import mega.triple.aaa.domain.forecast.daily.Get5DayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTodayForecastUC
import mega.triple.aaa.domain.forecast.daily.GetTomorrowForecastUC
import mega.triple.aaa.domain.forecast.model.DailyForecastDomainModel.Companion.toUiModel
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.widget.components.ForecastWidgetComposable
import mega.triple.aaa.widget.ext.createCell
import org.koin.compose.koinInject

class ForecastWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(
        setOf(
            createCell(1, 1), createCell(2, 1),
            createCell(1, 2), createCell(2, 2),
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val getTodayForecastUC: GetTodayForecastUC = koinInject()
            val getTomorrowForecastUC: GetTomorrowForecastUC = koinInject()
            val get5DayForecastUC: Get5DayForecastUC = koinInject()
            var today: DailyForecastUiModel? by remember { mutableStateOf(null) }
            var tomorrow: DailyForecastUiModel? by remember { mutableStateOf(null) }
            var list: List<DailyForecastUiModel> by remember { mutableStateOf(emptyList()) }

            LaunchedEffect("initialize") {
                getTodayForecastUC().onSuccess {
                    today = it.toUiModel()
                }
                getTomorrowForecastUC().onSuccess {
                    tomorrow = it.toUiModel()
                }
                get5DayForecastUC().onSuccess { forecast ->
                    list = forecast.map { it.toUiModel() }
                }
            }

            GlanceTheme {
                ForecastWidgetComposable(
                    today = today,
                    tomorrow = tomorrow,
                    list = list,
                )
            }
        }
    }
}

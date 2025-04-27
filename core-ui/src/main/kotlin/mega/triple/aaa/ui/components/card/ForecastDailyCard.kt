package mega.triple.aaa.ui.components.card

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.DrawingContext
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.copyColor
import mega.triple.aaa.common.ext.normalized
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.components.icon.CircleBgIcon
import mega.triple.aaa.ui.ext.formatShortDate
import mega.triple.aaa.ui.ext.formatTemperature
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography

@Composable
fun ForecastDailyCard(
    modifier: Modifier = Modifier,
    forecast: List<DailyForecastUiModel> = emptyList(),
    dayIndex: Int = 0,
) {
    if (forecast.isEmpty()) return

    Surface(
        color = colors.cardBG,
        contentColor = colors.cardContent,
        modifier = modifier
            .clip(AAATheme.shapes.cardShape),
    ) {
        Column(
            modifier = Modifier.padding(vertical = spaces.size12),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spaces.size8),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = spaces.size12)
            ) {
                CircleBgIcon(iconRes = drawable.ic_calendar)
                Text(
                    text = stringResource(string.common_day_forecast),
                    style = typography.ps400size14,
                )
            }
            LineGraph(
                forecast = forecast,
                dayIndex = dayIndex,
                modifier = Modifier.padding(horizontal = spaces.size16),
            )
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
private fun LineGraph(
    modifier: Modifier = Modifier,
    forecast: List<DailyForecastUiModel>,
    dayIndex: Int,
) {
    val temperatures = forecast.map { it.day?.wetBulbTemperature?.mathAverage ?: 0.0 }
    val dates = forecast.map { it.epochDate.normalized() }
    val unit = forecast.first().temperature?.minimum?.unit
    val contentColor = colors.chartArea.toArgb()
    val bottomPadding = spaces.size20

    val modelProducer = remember { CartesianChartModelProducer() }
    val defaultLine = rememberLineComponent(
        fill = Fill(colors.black.copy(alpha = .13f).toArgb()),
        thickness = spaces.size2,
        margins = Insets(horizontalDp = 7f),
    )
    val defaultLabel = rememberAxisLabelComponent(
        color = colors.black,
        textSize = typography.ps400size16.fontSize,
        padding = Insets.Zero,
        margins = Insets.Zero,
    )
    val defaultMarker = marker(
        label = defaultLabel,
        bottomPadding = bottomPadding,
        value = temperatures[dayIndex],
        unit = unit,
        contentColor = contentColor,
    )
    val transparentLine = rememberLineComponent(fill = Fill.Transparent)

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries {
                series(temperatures)
            }
        }
    }

    CartesianChartHost(
        modelProducer = modelProducer,
        scrollState = rememberVicoScrollState(scrollEnabled = false),
        animateIn = false,
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    listOf(
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(Fill.Black),
                            stroke = LineCartesianLayer.LineStroke.continuous(
                                cap = StrokeCap.Round,
                            ),
                            pointConnector = LineCartesianLayer.PointConnector.cubic(),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill = Fill(contentColor.copyColor(alpha = .2f)),
                            ),
                        )
                    )
                )
            ),
            startAxis = VerticalAxis.rememberStart(
                line = null,
                tick = transparentLine,
                tickLength = spaces.size16,
                itemPlacer = remember { VerticalAxis.ItemPlacer.count(count = { 3 }) },
                guideline = defaultLine,
                label = defaultLabel,
                valueFormatter = CartesianValueFormatter { _, value, _ ->
                    formatTemperature(
                        value = value,
                        unit = unit,
                    )
                }
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                line = defaultLine.copy(margins = Insets(horizontalDp = 9f)),
                tick = transparentLine,
                tickLength = bottomPadding,
                guideline = null,
                label = defaultLabel,
                valueFormatter = CartesianValueFormatter { _, value, _ ->
                    formatShortDate(dates[value.toInt()])
                }
            ),
            persistentMarkers = { defaultMarker.at(dayIndex) },
        ),
        modifier = modifier,
    )
}

@Composable
private fun marker(
    label: TextComponent,
    bottomPadding: Dp,
    value: Double,
    unit: String?,
    contentColor: Int,
): DefaultCartesianMarker {
    var markerHeight by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current.density

    return rememberDefaultCartesianMarker(
        label = label.copy(
            padding = Insets(bottomDp = bottomPadding.value),
            background = markerBackground(),
        ),
        valueFormatter = DefaultCartesianMarker.ValueFormatter { _, _ ->
            formatTemperature(
                value = value,
                unit = unit,
            )
        },
        labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint,
        indicator = {
            indicator(
                contentColor = contentColor,
                onHeightChange = { markerHeight = it },
            )
        },
        guideline = rememberAxisGuidelineComponent(
            fill = Fill(contentColor),
            thickness = spaces.size2,
            margins = Insets(topDp = markerHeight / density - 2 * bottomPadding.value),
        ),
    )
}

private fun markerBackground(): Component = object : Component {
    override fun draw(
        context: DrawingContext,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        val paint = Paint().apply {
            color = Color.WHITE
        }

        val width = left + (right - left) / 2f
        val height = top + (bottom - top) / 2f

        with(context.canvas) {
            drawCircle(
                width,
                height,
                15f,
                paint,
            )
            drawCircle(
                width + 35f,
                height - 25f,
                30f,
                paint,
            )
            drawCircle(
                width - 35f,
                height - 25f,
                30f,
                paint,
            )
            drawRect(
                width - 35f,
                height - 55f,
                width + 35f,
                height + 5f,
                paint
            )
        }
    }
}

private fun indicator(
    contentColor: Int,
    onHeightChange: (Float) -> Unit,
) = object : Component {
    override fun draw(
        context: DrawingContext,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        val width = left + (right - left) / 2f
        val height = top + (bottom - top) / 2f
        onHeightChange(height)
        with(context.canvas) {
            drawCircle(
                width,
                height,
                25f,
                Paint().apply {
                    color = Color.WHITE
                },
            )
            drawCircle(
                width,
                height,
                15f,
                Paint().apply {
                    color = contentColor
                },
            )
        }
    }
}


@Preview
@Composable
private fun PreviewForecastDailyCard() = AAATheme {
    ForecastDailyCard()
}
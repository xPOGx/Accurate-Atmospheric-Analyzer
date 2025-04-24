package mega.triple.aaa.widget.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.size
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.unit.ColorProvider
import mega.triple.aaa.common.ext.isToday
import mega.triple.aaa.common.ext.normalized
import mega.triple.aaa.strings.R
import mega.triple.aaa.ui.R.drawable
import mega.triple.aaa.ui.ext.formatDate
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.widget.ext.CellInfo.CELL_1_1
import mega.triple.aaa.widget.ext.CellInfo.CELL_1_2
import mega.triple.aaa.widget.ext.CellInfo.CELL_2_1
import mega.triple.aaa.widget.ext.CellInfo.CELL_2_2
import mega.triple.aaa.widget.ext.cellInfo
import mega.triple.aaa.widget.ext.createCell

@SuppressLint("RestrictedApi")
@Composable
internal fun ForecastWidgetComposable(
    modifier: GlanceModifier = GlanceModifier,
    today: DailyForecastUiModel? = null,
    tomorrow: DailyForecastUiModel? = null,
    list: List<DailyForecastUiModel> = emptyList(),
) {
    val size = LocalSize.current
    val context = LocalContext.current
    val cellInfo = size.cellInfo()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.width, size.height),
    ) {
        Image(
            provider = ImageProvider(drawable.img_bg_toolbar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        when (cellInfo) {
            CELL_1_1 -> when (today) {
                null -> ForecastEmpty()
                else -> ForecastDay(
                    data = today,
                    title = context.getString(R.string.common_today)
                )
            }

            CELL_1_2 -> when {
                today == null || tomorrow == null -> ForecastEmpty()
                else -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier,
                ) {
                    ForecastDay(
                        data = today,
                        title = context.getString(R.string.common_today),
                        modifier = GlanceModifier.defaultWeight(),
                    )
                    Image(
                        provider = ImageProvider(drawable.ic_divider),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(ColorProvider(Color.White)),
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .height(AAATheme.spaces.size8)
                    )
                    ForecastDay(
                        data = tomorrow,
                        title = context.getString(R.string.common_tomorrow),
                        modifier = GlanceModifier.defaultWeight(),
                    )
                }
            }

            CELL_2_1 -> when {
                today == null || tomorrow == null -> ForecastEmpty()
                else -> Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ForecastDay(
                        data = today,
                        title = context.getString(R.string.common_today),
                    )
                    ForecastDay(
                        data = tomorrow,
                        title = context.getString(R.string.common_tomorrow),
                    )
                }
            }

            CELL_2_2 -> when {
                list.isEmpty() -> ForecastEmpty()
                else -> Column(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        list.take(2).forEach {
                            val dateTime = it.epochDate.normalized()
                            ForecastDay(
                                data = it,
                                title = when {
                                    dateTime.isToday() -> context.getString(R.string.common_today)
                                    else -> formatDate(dateTime)
                                },
                            )
                        }
                    }
                    Image(
                        provider = ImageProvider(drawable.ic_divider),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        colorFilter = ColorFilter.tint(ColorProvider(Color.White)),
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .height(AAATheme.spaces.size8)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        list.drop(2).take(2).forEach {
                            ForecastDay(
                                data = it,
                                title = formatDate(it.epochDate.normalized()),
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastWidgetComposablePreview11() {
    CompositionLocalProvider(LocalSize provides createCell(1, 1)) {
        GlanceTheme {
            ForecastWidgetComposable(
                today = DailyForecastUiModel.NULL,
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastWidgetComposablePreview21() {
    CompositionLocalProvider(LocalSize provides createCell(2, 1)) {
        GlanceTheme {
            ForecastWidgetComposable(
                today = DailyForecastUiModel.NULL,
                tomorrow = DailyForecastUiModel.NULL,
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastWidgetComposablePreview12() {
    CompositionLocalProvider(LocalSize provides createCell(1, 2)) {
        GlanceTheme {
            ForecastWidgetComposable(
                today = DailyForecastUiModel.NULL,
                tomorrow = DailyForecastUiModel.NULL,
            )
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastWidgetComposablePreview22() {
    CompositionLocalProvider(LocalSize provides createCell(2, 2)) {
        GlanceTheme {
            ForecastWidgetComposable(
                list = listOf(
                    DailyForecastUiModel.NULL,
                    DailyForecastUiModel.NULL,
                    DailyForecastUiModel.NULL,
                    DailyForecastUiModel.NULL,
                )
            )
        }
    }
}

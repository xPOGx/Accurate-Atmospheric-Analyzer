package mega.triple.aaa.widget.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.size
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import mega.triple.aaa.ui.ext.formatTemperature
import mega.triple.aaa.ui.ext.getAccuWeatherIconRes
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.widget.ext.defaultTextStyle

@SuppressLint("RestrictedApi")
@Composable
internal fun ForecastDay(
    modifier: GlanceModifier = GlanceModifier,
    data: DailyForecastUiModel? = null,
    title: String,
) {
    val weatherIcon = getAccuWeatherIconRes(data?.day?.icon)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (title.contains(", ")) {
            title.split(", ").forEach {
                Text(
                    text = it,
                    style = defaultTextStyle.copy(fontSize = 12.sp),
                )
            }
        } else {
            Text(
                text = title,
                style = defaultTextStyle.copy(fontSize = 12.sp),
            )
        }
        Image(
            provider = ImageProvider(weatherIcon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = GlanceModifier.size(AAATheme.spaces.size54)
        )
        Text(
            text = formatTemperature(
                data?.day?.wetBulbTemperature?.average?.value
                    ?: data?.day?.wetBulbTemperature?.mathAverage,
                data?.day?.wetBulbTemperature?.maximum?.unit,
            ),
            style = defaultTextStyle,
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastDayPreview() {
    GlanceTheme {
        ForecastDay(
            title = "Hello World!",
            data = DailyForecastUiModel.NULL,
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastDayPreviewEmpty() {
    GlanceTheme {
        ForecastDay(
            title = "Empty"
        )
    }
}

package mega.triple.aaa.widget.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import mega.triple.aaa.main.AAAActivity
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.widget.ext.cellInfo
import mega.triple.aaa.widget.ext.createCell
import mega.triple.aaa.widget.ext.defaultTextStyle

@SuppressLint("RestrictedApi")
@Composable
internal fun ForecastEmpty(
    modifier: GlanceModifier = GlanceModifier,
) {
    val cellInfo = LocalSize.current.cellInfo()
    val small = cellInfo.info.startsWith("1")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        val textButton = if (small)
            "O\nP\nE\nN" else "OPEN"
        if (!small) {
            Text("NO DATA", style = defaultTextStyle)
            Spacer(GlanceModifier.height(AAATheme.spaces.size4))
        }
        if (small && !cellInfo.info.endsWith("1")) {
            Text("NO", style = defaultTextStyle)
            Text("D\nA\nT\nA", style = defaultTextStyle)
            Spacer(GlanceModifier.height(AAATheme.spaces.size4))
        }
        Button(textButton, { actionStartActivity<AAAActivity>() })
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastEmptyPreview11() {
    CompositionLocalProvider(LocalSize provides createCell(1, 1)) {
        GlanceTheme {
            ForecastEmpty()
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastEmptyPreview21() {
    CompositionLocalProvider(LocalSize provides createCell(2, 1)) {
        GlanceTheme {
            ForecastEmpty()
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastEmptyPreview12() {
    CompositionLocalProvider(LocalSize provides createCell(1, 2)) {
        GlanceTheme {
            ForecastEmpty()
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview
@Composable
private fun ForecastEmptyPreview22() {
    CompositionLocalProvider(LocalSize provides createCell(2, 2)) {
        GlanceTheme {
            ForecastEmpty()
        }
    }
}
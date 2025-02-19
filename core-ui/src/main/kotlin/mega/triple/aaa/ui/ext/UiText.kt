package mega.triple.aaa.ui.ext

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class Dynamic(val text: String) : UiText
    data class Resource(@StringRes val textRes: Int) : UiText

    companion object {
        @Composable
        fun UiText.asString(): String = when (this) {
            is Dynamic -> this.text
            is Resource -> stringResource(this.textRes)
        }

        fun UiText.asString(context: Context): String = when (this) {
            is Dynamic -> this.text
            is Resource -> context.getString(this.textRes)
        }
    }
}

fun Throwable.asUiText(default: UiText = UiText.Dynamic("Unknown Error")): UiText =
    this.message?.let(UiText::Dynamic) ?: default

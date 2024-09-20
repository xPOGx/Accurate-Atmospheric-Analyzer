package mega.triple.aaa.presentation.core.ui.ext

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.triple.aaa.presentation.core.ui.components.card.ErrorCard

sealed class UI<out T> {
    data object LOADING : UI<Nothing>()

    class READY<out T>(val data: T) : UI<T>()

    class ERROR(
        val e: Throwable,
        val onTryAgain: (() -> Unit)? = null,
    ) : UI<Nothing>()
}

@SuppressLint("ComposableNaming")
@Composable
fun <T> UI<T>.render(
    onLoading: @Composable () -> Unit = {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    },
    onError: @Composable (Throwable, (() -> Unit)?) -> Unit = { e, action ->
        ErrorCard(
            errorMsg = e.message ?: "Unknown error",
            onTryAgain = action,
        )
    },
    onDone: @Composable (T) -> Unit,
) {
    when (this) {
        UI.LOADING -> onLoading()
        is UI.READY -> onDone(this.data)
        is UI.ERROR -> onError(this.e, this.onTryAgain)
    }
}

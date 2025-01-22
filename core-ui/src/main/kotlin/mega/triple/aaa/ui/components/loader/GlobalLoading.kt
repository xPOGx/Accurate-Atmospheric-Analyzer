package mega.triple.aaa.ui.components.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import mega.triple.aaa.ui.theme.AAATheme

@Composable
fun GlobalLoading(
    modifier: Modifier = Modifier,
    withBackground: Boolean = false,
) {
    val body: @Composable () -> Unit = {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxWidth(.2f)
            )
        }
    }

    if (withBackground) {
        Dialog(
            onDismissRequest = { /* ignore */ },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
            content = body,
        )
    } else {
        body()
    }
}

@Preview
@Composable
private fun GlobalLoadingPreview() {
    AAATheme {
        GlobalLoading()
    }
}

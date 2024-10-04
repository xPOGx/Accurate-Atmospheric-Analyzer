package mega.triple.aaa.presentation.core.ui.components.view

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.view.DottedCircleProgressBar

@Composable
fun UvIndexView(
    uvIndex: Float,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { ctx ->
            DottedCircleProgressBar(ctx).apply {
                setup(progress = uvIndex)
            }
        },
        modifier = modifier.size(spaces.size100)
    )
}

@Preview
@Composable
private fun UvIndexViewPreview() {
    AAATheme {
        UvIndexView(2f)
    }
}
package mega.triple.aaa.presentation.core.ui.components.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.view.DottedCircleProgressBar

@Composable
fun UvIndexView(
    uvIndex: Float,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.cardBG,
            contentColor = colors.cardContent,
        ),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { ctx ->
                    DottedCircleProgressBar(ctx).apply {
                        setup(progress = uvIndex)
                    }
                },
                modifier = Modifier.size(spaces.size100)
            )
        }
    }
}

@Preview
@Composable
private fun UvIndexViewPreview() {
    AAATheme {
        UvIndexView(2f)
    }
}
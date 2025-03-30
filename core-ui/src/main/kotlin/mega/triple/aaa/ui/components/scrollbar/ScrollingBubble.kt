package mega.triple.aaa.ui.components.scrollbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.triple.aaa.ui.theme.AAATheme

val bubbleSize = 48.dp

@Composable
fun ScrollingBubble(
    boxConstraintMaxWidth: Dp,
    bubbleOffsetYFloat: Float,
    currAlphabetScrolledOn: Char,
) {
    Box(
        modifier = Modifier
            .size(bubbleSize)
            .offset(
                x = (boxConstraintMaxWidth - (bubbleSize + alphabetItemSize)),
                y = with(LocalDensity.current) {
                    bubbleOffsetYFloat.toDp() - (bubbleSize / 2)
                },
            )
            .background(AAATheme.colors.cardContent, CircleShape),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currAlphabetScrolledOn.toString(),
                style = AAATheme.typography.gs400size18,
                color = AAATheme.colors.white,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollingBubblePreview() {
    val density = LocalDensity.current
    AAATheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ScrollingBubble(
                boxConstraintMaxWidth = this.maxWidth,
                bubbleOffsetYFloat = with(density) { (bubbleSize / 2).toPx() },
                currAlphabetScrolledOn = 'A',
            )
        }
    }
}

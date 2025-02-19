package mega.triple.aaa.ui.components.scrollbar

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.spaces

val alphabetItemSize = 24.dp

@Composable
fun AlphabetScroller(
    onAlphabetListDrag: (relativeDragYOffset: Float?, distanceFromTopOfScreen: Float) -> Unit,
) {
    val alphabetCharList = "abcdefghijklmnopqrstuvwxyz".map { it }
    var distanceFromTopOfScreen by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .width(spaces.size16)
            .onGloballyPositioned {
                distanceFromTopOfScreen = it.positionInParent().y
            }
            .pointerInput(alphabetCharList) {
                detectVerticalDragGestures(
                    onDragStart = {
                        onAlphabetListDrag(it.y, distanceFromTopOfScreen)
                    },
                    onDragEnd = {
                        onAlphabetListDrag(null, distanceFromTopOfScreen)
                    }
                ) { change, _ ->
                    onAlphabetListDrag(change.position.y, distanceFromTopOfScreen)
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        for (i in alphabetCharList) {
            Text(
                modifier = Modifier.height(alphabetItemSize),
                text = i.toString(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlphabetScrollerPreview() {
    AAATheme {
        AlphabetScroller { _, _ ->

        }
    }
}

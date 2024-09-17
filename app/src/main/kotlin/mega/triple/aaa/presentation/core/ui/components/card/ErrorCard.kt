package mega.triple.aaa.presentation.core.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.components.ext.SpacerHeight
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.shapes
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorMsg: String,
    onTryAgain: (() -> Unit)? = null,
) {
    Box(modifier = modifier.padding(top = spaces.size8)) {
        Card(
            shape = RoundedCornerShape(spaces.size24),
            modifier = Modifier.padding(spaces.size24)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spaces.size24)
                    .padding(
                        top = spaces.size24,
                        bottom = spaces.size16,
                    )
            ) {
                Text(
                    text = "Error!",
                    style = typography.ps700size36
                )
                SpacerHeight(spaces.size16)
                Text(
                    text = errorMsg,
                    style = typography.gs400size18
                )
                TextButton(onClick = { onTryAgain?.invoke() }) {
                    Text(text = "Try again")
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(spaces.size48)
                .background(
                    Color.Red.copy(alpha = .8f),
                    shapes.circleShape,
                )
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = colors.white,
                modifier = Modifier.size(spaces.size36)
            )
        }
    }
}

@Preview
@Composable
private fun ErrorCardPreview() {
    AAATheme {
        ErrorCard(errorMsg = "Ooops")
    }
}

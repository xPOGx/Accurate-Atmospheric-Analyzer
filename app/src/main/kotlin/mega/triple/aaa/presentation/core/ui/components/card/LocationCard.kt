package mega.triple.aaa.presentation.core.ui.components.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.colors
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.spaces
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    title: String,
    onClick: (() -> Unit)? = null,
) {
    Card(
        onClick = { onClick?.invoke() },
        colors = CardDefaults.cardColors().copy(
            containerColor = colors.cardBG,
            contentColor = colors.cardContent,
        ),
        modifier = modifier,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = typography.ps400size18,
            modifier = Modifier
                .fillMaxWidth()
                .padding(spaces.size16)
        )
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    AAATheme {
        LocationCard(title = "Title")
    }
}

package mega.triple.aaa.presentation.feature.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.components.card.LocationCard
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography

@Composable
fun LocationChooseCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = typography.ps400size16,
            modifier = Modifier.weight(0.3f),
        )
        LocationCard(
            title = value ?: "Choose ${title.lowercase()}",
            modifier = Modifier.weight(0.7f)
        ) {
            onClick?.invoke()
        }
    }
}

@Preview
@Composable
private fun LocationChooseCardPreview() {
    AAATheme {
        LocationChooseCard(title = "Title")
    }
}

@Preview
@Composable
private fun LocationChooseCardPreviewValue() {
    AAATheme {
        LocationChooseCard(title = "Title", value = "Value")
    }
}

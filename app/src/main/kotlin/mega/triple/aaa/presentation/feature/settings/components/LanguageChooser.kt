package mega.triple.aaa.presentation.feature.settings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType

@Composable
fun LanguageChooser(
    modifier: Modifier = Modifier,
    index: ThemeType = ThemeType.AUTO,
    onClick: ((ThemeType) -> Unit)? = null,
) {
    TabRow(
        selectedTabIndex = index.type + 1,
        containerColor = AAATheme.colors.tabContainer,
        contentColor = AAATheme.colors.tabContent,
        modifier = modifier
    ) {
        Tab(
            selected = false,
            onClick = { /* ignore */ },
            enabled = false,
        ) {
            Text(
                "Language",
                style = AAATheme.typography.ps400size18
            )
        }
        Tab(
            selected = index == ThemeType.AUTO,
            enabled = false,
            onClick = { onClick?.invoke(ThemeType.AUTO) },
            modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
        ) {
            Text(
                "English",
                style = AAATheme.typography.ps700size18
            )
        }
        Tab(
            selected = index == ThemeType.DYNAMIC,
            enabled = false,
            onClick = { onClick?.invoke(ThemeType.DYNAMIC) },
            modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
        ) {
            Text(
                "Ukrainian",
                style = AAATheme.typography.ps700size18
            )
        }
    }
}

@Preview
@Composable
private fun LanguageChooserPreview() {
    AAATheme {
        LanguageChooser()
    }
}

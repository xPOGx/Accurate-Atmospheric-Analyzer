package mega.triple.aaa.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.strings.R.string
import mega.triple.aaa.ui.model.ThemeTypeUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.AAATheme.colors
import mega.triple.aaa.ui.theme.AAATheme.spaces
import mega.triple.aaa.ui.theme.AAATheme.typography

// TODO add measure model
@Composable
fun MeasureChooser(
    modifier: Modifier = Modifier,
    index: ThemeTypeUiModel = ThemeTypeUiModel.AUTO,
    onClick: ((ThemeTypeUiModel) -> Unit)? = null,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(string.settings_measure),
            style = typography.ps400size18,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = spaces.size16)
        )
        TabRow(
            selectedTabIndex = index.type,
            containerColor = colors.tabContainer,
            contentColor = colors.tabContent,
            modifier = modifier
        ) {
            Tab(
                selected = index == ThemeTypeUiModel.AUTO,
                enabled = false,
                onClick = { onClick?.invoke(ThemeTypeUiModel.AUTO) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_metric),
                    style = typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeTypeUiModel.DYNAMIC,
                enabled = false,
                onClick = { onClick?.invoke(ThemeTypeUiModel.DYNAMIC) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_imperial),
                    style = typography.ps700size18
                )
            }
        }
    }
}

@Preview
@Composable
private fun LanguageChooserPreview() {
    AAATheme {
        MeasureChooser()
    }
}

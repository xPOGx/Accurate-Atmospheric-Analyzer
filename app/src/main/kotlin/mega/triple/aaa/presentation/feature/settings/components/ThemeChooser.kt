package mega.triple.aaa.presentation.feature.settings.components

import android.os.Build
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

@Composable
fun ThemeChooser(
    modifier: Modifier = Modifier,
    index: ThemeTypeUiModel = ThemeTypeUiModel.LIGHT,
    onClick: ((ThemeTypeUiModel) -> Unit)? = null,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Theme",
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
                onClick = { onClick?.invoke(ThemeTypeUiModel.AUTO) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_auto),
                    style = typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeTypeUiModel.DYNAMIC,
                enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                onClick = { onClick?.invoke(ThemeTypeUiModel.DYNAMIC) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_dynamic),
                    style = typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeTypeUiModel.DARK,
                onClick = { onClick?.invoke(ThemeTypeUiModel.DARK) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_dark),
                    style = typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeTypeUiModel.LIGHT,
                onClick = { onClick?.invoke(ThemeTypeUiModel.LIGHT) },
                modifier = Modifier.padding(vertical = spaces.size8)
            ) {
                Text(
                    stringResource(string.settings_light),
                    style = typography.ps700size18
                )
            }
        }
    }
}

@Preview
@Composable
private fun ThemeChooserPreview() {
    AAATheme {
        ThemeChooser()
    }
}

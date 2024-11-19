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
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType

@Composable
fun ThemeChooser(
    modifier: Modifier = Modifier,
    index: ThemeType = ThemeType.LIGHT,
    onClick: ((ThemeType) -> Unit)? = null,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Theme",
            style = AAATheme.typography.ps400size18,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = AAATheme.spaces.size16)
        )
        TabRow(
            selectedTabIndex = index.type,
            containerColor = AAATheme.colors.tabContainer,
            contentColor = AAATheme.colors.tabContent,
            modifier = modifier
        ) {
            Tab(
                selected = index == ThemeType.AUTO,
                onClick = { onClick?.invoke(ThemeType.AUTO) },
                modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
            ) {
                Text(
                    "Auto",
                    style = AAATheme.typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeType.DYNAMIC,
                enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                onClick = { onClick?.invoke(ThemeType.DYNAMIC) },
                modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
            ) {
                Text(
                    "Dynamic",
                    style = AAATheme.typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeType.DARK,
                onClick = { onClick?.invoke(ThemeType.DARK) },
                modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
            ) {
                Text(
                    "Dark",
                    style = AAATheme.typography.ps700size18
                )
            }
            Tab(
                selected = index == ThemeType.LIGHT,
                onClick = { onClick?.invoke(ThemeType.LIGHT) },
                modifier = Modifier.padding(vertical = AAATheme.spaces.size8)
            ) {
                Text(
                    "Light",
                    style = AAATheme.typography.ps700size18
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

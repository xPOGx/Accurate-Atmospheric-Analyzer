package mega.triple.aaa.presentation.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.core.ui.theme.AAATheme.typography
import mega.triple.aaa.presentation.feature.settings.components.LanguageChooser
import mega.triple.aaa.presentation.feature.settings.components.MeasureChooser
import mega.triple.aaa.presentation.feature.settings.components.ThemeChooser
import mega.triple.aaa.presentation.feature.settings.ext.SettingsAction
import mega.triple.aaa.presentation.feature.settings.ext.SettingsAction.OnNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsUiState = SettingsUiState(),
    onAction: ((SettingsAction) -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = typography.ps400size22,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction?.invoke(OnNavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                modifier = Modifier.displayCutoutPadding(),
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(AAATheme.spaces.size16),
            modifier = Modifier.padding(innerPadding)
        ) {
            ThemeChooser(
                index = uiState.themeType,
                onClick = { onAction?.invoke(SettingsAction.OnThemeChange(it)) }
            )
            LanguageChooser()
            MeasureChooser()
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    AAATheme {
        SettingsScreen()
    }
}

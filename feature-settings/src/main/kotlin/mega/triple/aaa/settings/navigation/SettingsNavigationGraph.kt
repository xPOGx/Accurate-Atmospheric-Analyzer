package mega.triple.aaa.settings.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import mega.triple.aaa.navigation.NavigationDirection
import mega.triple.aaa.settings.SettingsScreen
import mega.triple.aaa.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.settingsNavigationGraph(
    navController: NavHostController,
) {
    navigation<NavigationDirection.Settings>(
        startDestination = SettingsDirection.Settings,
    ) {
        composable<SettingsDirection.Settings> {
            val viewModel = koinViewModel<SettingsViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            with(viewModel) {
                onNavigationBack.collectEffect { navController.navigateUp() }
            }

            SettingsScreen(
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }
    }
}

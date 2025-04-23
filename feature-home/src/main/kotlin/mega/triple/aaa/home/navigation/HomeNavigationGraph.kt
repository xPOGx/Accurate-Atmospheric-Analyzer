package mega.triple.aaa.home.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import mega.triple.aaa.home.HomeScreen
import mega.triple.aaa.home.HomeViewModel
import mega.triple.aaa.navigation.NavigationDirection
import mega.triple.aaa.ui.components.loader.GlobalLoading
import mega.triple.aaa.ui.ext.UiText.Companion.asString
import mega.triple.aaa.ui.ext.render
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeNavigationGraph(
    navController: NavHostController,
) {
    navigation<NavigationDirection.Home>(
        startDestination = HomeDirection.Home,
    ) {
        composable<HomeDirection.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current

            with(viewModel) {
                onNavigateToSearch.collectEffect {
                    navController.navigate(NavigationDirection.Search)
                }
                onNavigateToSettings.collectEffect {
                    navController.navigate(NavigationDirection.Settings)
                }
                onToast.collectEffect { msg ->
                    Toast.makeText(context, msg.asString(context), Toast.LENGTH_SHORT).show()
                }
            }

            uiState.location.render(
                onLoading = { GlobalLoading() }
            ) { location ->
                HomeScreen(
                    location = location,
                    uiState = uiState,
                    onAction = viewModel::onAction,
                )
            }

            if (uiState.forecastFlows.isLoading) {
                GlobalLoading(withBackground = true)
            }
        }
    }
}

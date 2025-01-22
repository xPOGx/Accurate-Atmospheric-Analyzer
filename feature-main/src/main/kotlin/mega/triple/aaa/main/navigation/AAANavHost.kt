package mega.triple.aaa.main.navigation

import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mega.triple.aaa.home.HomeScreen
import mega.triple.aaa.home.HomeViewModel
import mega.triple.aaa.main.navigation.ext.Routes
import mega.triple.aaa.search.SearchScreen
import mega.triple.aaa.search.SearchViewModel
import mega.triple.aaa.settings.SettingsScreen
import mega.triple.aaa.settings.SettingsViewModel
import mega.triple.aaa.ui.components.loader.GlobalLoading
import mega.triple.aaa.ui.ext.render

@Composable
fun AAANavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.HOME,
        modifier = modifier,
    ) {
        composable(route = Routes.HOME) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            uiState.location.render(
                onLoading = { GlobalLoading(withBackground = false) }
            ) { location ->
                HomeScreen(
                    location = location,
                    forecastFlows = uiState.forecastFlows,
                    navigateToSearch = { navHostController.navigate(Routes.SEARCH) },
                    navigateToSettings = { navHostController.navigate(Routes.SETTINGS) }
                )
            }
        }
        composable(
            route = Routes.SEARCH,
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } },
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current

            with(viewModel) {
                onSaveSuccess.collectEffect {
                    Toast.makeText(context, uiState.location.locationName, Toast.LENGTH_LONG).show()
                    navHostController.navigateUp()
                }
                onNavigationBack.collectEffect { navHostController.navigateUp() }
                onToast.collectEffect { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
            }

            SearchScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
            )
        }
        composable(
            route = Routes.SETTINGS,
            enterTransition = { slideInVertically { -it } },
            exitTransition = { slideOutVertically { -it } },
        ) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            with(viewModel) {
                onNavigationBack.collectEffect { navHostController.navigateUp() }
            }

            SettingsScreen(
                uiState = uiState,
                onAction = viewModel::onAction
            )
        }
    }
}

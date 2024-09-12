package mega.triple.aaa.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mega.triple.aaa.presentation.feature.home.HomeScreen
import mega.triple.aaa.presentation.feature.search.SearchScreen
import mega.triple.aaa.presentation.feature.search.SearchViewModel
import mega.triple.aaa.presentation.navigation.ext.Routes

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
            HomeScreen(
                navigateToSearch = { navHostController.navigate(Routes.SEARCH) },
            )
        }
        composable(
            route = Routes.SEARCH,
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } },
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            SearchScreen(
                uiState = uiState,
                loadList = viewModel::loadList,
                onAction = viewModel::onAction,
                onNavigateBack = { navHostController.navigateUp() }
            )
        }
    }
}

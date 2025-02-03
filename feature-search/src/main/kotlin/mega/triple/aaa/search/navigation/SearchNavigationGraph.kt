package mega.triple.aaa.search.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import mega.triple.aaa.navigation.NavigationDirection
import mega.triple.aaa.search.SearchScreen
import mega.triple.aaa.search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.searchNavigationGraph(
    navController: NavHostController,
) {
    navigation<NavigationDirection.Search>(
        startDestination = SearchDirection.Search,
    ) {
        composable<SearchDirection.Search> {
            val viewModel = koinViewModel<SearchViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current

            with(viewModel) {
                onSaveSuccess.collectEffect {
                    Toast.makeText(context, uiState.location.locationName, Toast.LENGTH_LONG).show()
                    navController.navigateUp()
                }
                onNavigationBack.collectEffect { navController.navigateUp() }
                onToast.collectEffect { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
            }

            SearchScreen(
                uiState = uiState,
                onAction = viewModel::onAction,
            )
        }
    }
}

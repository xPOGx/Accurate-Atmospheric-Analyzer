package mega.triple.aaa.presentation.navigation

import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mega.triple.aaa.presentation.core.ui.ext.render
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.feature.home.HomeScreen
import mega.triple.aaa.presentation.feature.home.HomeViewModel
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
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            uiState.location.render(
                onLoading = { GlobalLoading(withBackground = false) }
            ) { location ->
                HomeScreen(
                    location = location,
                    navigateToSearch = { navHostController.navigate(Routes.SEARCH) },
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
    }
}

@Composable
fun GlobalLoading(
    modifier: Modifier = Modifier,
    withBackground: Boolean = false,
) {
    val body: @Composable () -> Unit = {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .displayCutoutPadding()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxWidth(.2f)
            )
        }
    }

    if (withBackground) {
        Dialog(
            onDismissRequest = { /* ignore */ },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
            content = body,
        )
    } else {
        body()
    }
}

@Preview
@Composable
private fun GlobalLoadingPreview() {
    AAATheme {
        GlobalLoading()
    }
}

package mega.triple.aaa.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import mega.triple.aaa.home.navigation.homeNavigationGraph
import mega.triple.aaa.navigation.NavigationDirection
import mega.triple.aaa.search.navigation.searchNavigationGraph
import mega.triple.aaa.settings.navigation.settingsNavigationGraph

@Composable
fun AAANavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDirection.Home,
        modifier = modifier,
    ) {
        homeNavigationGraph(navController)
        searchNavigationGraph(navController)
        settingsNavigationGraph(navController)
    }
}

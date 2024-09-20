package mega.triple.aaa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mega.triple.aaa.presentation.core.ui.ext.render
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.feature.search.SearchScreen
import mega.triple.aaa.presentation.feature.search.SearchViewModel
import mega.triple.aaa.presentation.navigation.AAANavHost
import mega.triple.aaa.presentation.navigation.GlobalLoading

@AndroidEntryPoint
class AAAActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fullscreen
        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }

        // Edge to edge
        enableEdgeToEdge()

        // Content
        setContent {
            val navHostController = rememberNavController()
            val mainViewModel = hiltViewModel<AAAViewModel>()
            val location by mainViewModel.location.collectAsStateWithLifecycle()

            location.render(
                onLoading = { GlobalLoading(withBackground = false) }
            ) {
                var isLoading by remember { mutableStateOf(false) }

                AAATheme {
                    if (isLoading) {
                        GlobalLoading()
                    }

                    AnimatedContent(
                        targetState = it != null, label = "MainScreen",
                    ) { isValid ->
                        if (isValid) {
                            AAANavHost(navHostController = navHostController)
                        } else {
                            val viewModel = hiltViewModel<SearchViewModel>()
                            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                            val context = LocalContext.current


                            with(viewModel) {
                                LaunchedEffect("onSaveSuccess") {
                                    onSaveSuccess.collect { isLoading = true }
                                }
                                LaunchedEffect("onToast") {
                                    onToast.collect { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_LONG)
                                    }
                                }
                            }

                            SearchScreen(
                                uiState = uiState,
                                forceMode = true,
                                onAction = viewModel::onAction,
                            )
                        }
                    }
                }
            }
        }
    }
}

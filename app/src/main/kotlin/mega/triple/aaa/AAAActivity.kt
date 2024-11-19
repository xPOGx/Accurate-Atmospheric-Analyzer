package mega.triple.aaa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint
import mega.triple.aaa.presentation.core.ui.components.loader.GlobalLoading
import mega.triple.aaa.presentation.core.ui.ext.render
import mega.triple.aaa.presentation.core.ui.theme.AAATheme
import mega.triple.aaa.presentation.feature.analytic.AAAAnalytic
import mega.triple.aaa.presentation.feature.search.SearchScreen
import mega.triple.aaa.presentation.feature.search.SearchViewModel
import mega.triple.aaa.presentation.feature.setting.ext.ThemeType
import mega.triple.aaa.presentation.navigation.AAANavHost

@AndroidEntryPoint
class AAAActivity : ComponentActivity() {
    private val activityStart = System.currentTimeMillis()

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

        // Firebase
        Firebase.initialize(this)
        AAAAnalytic.subscribe {
            Firebase.analytics.logEvent(it, null)
        }

        // Content
        setContent {
            val navHostController = rememberNavController()
            val mainViewModel = hiltViewModel<AAAViewModel>()
            val location by mainViewModel.location.collectAsStateWithLifecycle()
            val themeType by mainViewModel.themeType.collectAsStateWithLifecycle(ThemeType.AUTO)

            val context = LocalContext.current

            AAATheme(themeType) {
                location.render(
                    onLoading = { GlobalLoading(withBackground = false) }
                ) {
                    AnimatedContent(targetState = it != null, label = "MainScreen") { isValid ->
                        if (isValid) {
                            AAANavHost(navHostController = navHostController)
                        } else {
                            val viewModel = hiltViewModel<SearchViewModel>()
                            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                            with(viewModel) {
                                onSaveSuccess.collectEffect {
                                    Toast.makeText(
                                        context,
                                        uiState.location.locationName,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                onToast.collectEffect { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        logSessionTime()
        super.onDestroy()
    }

    private fun logSessionTime() {
        val activityEnd = System.currentTimeMillis()
        val minutes = activityEnd.minus(activityStart).div(1000).div(60)
        Firebase.analytics.logEvent("SessionTime") {
            param("start: ", activityStart)
            param("end: ", activityEnd)
            param("minutes: ", minutes)
        }
    }
}

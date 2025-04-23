package mega.triple.aaa.main

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mega.triple.aaa.common.ext.showToast
import mega.triple.aaa.domain.settings.GetThemeUC
import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel.Companion.toUiModel
import mega.triple.aaa.main.navigation.AAANavHost
import mega.triple.aaa.search.SearchScreen
import mega.triple.aaa.search.SearchViewModel
import mega.triple.aaa.ui.components.loader.GlobalLoading
import mega.triple.aaa.ui.ext.UiText.Companion.asString
import mega.triple.aaa.ui.ext.render
import mega.triple.aaa.ui.model.ThemeTypeUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.darkColors
import mega.triple.aaa.ui.theme.lightColors
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class AAAActivity : ComponentActivity() {
    private val activityStart = System.currentTimeMillis()

    private val getThemeUC: GetThemeUC by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.lifecycleScope.launch {
            getThemeUC().collectLatest { settingTheme ->
                setupEdgeToEdge(settingTheme.toUiModel())
            }
        }

        // Content
        setContent {
            val navHostController = rememberNavController()
            val mainViewModel = koinViewModel<AAAViewModel>()
            val location by mainViewModel.location.collectAsStateWithLifecycle()
            val themeType by mainViewModel.themeType.collectAsStateWithLifecycle(ThemeTypeUiModel.LIGHT)

            AAATheme(themeType) {
                location.render(
                    onLoading = { GlobalLoading(withBackground = false) }
                ) {
                    AnimatedContent(
                        targetState = it != null,
                        label = "MainScreen",
                    ) { isValid ->
                        if (isValid) {
                            AAANavHost(navController = navHostController)
                        } else {
                            val viewModel = koinViewModel<SearchViewModel>()
                            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                            with(viewModel) {
                                onSaveSuccess.collectEffect {
                                    this@AAAActivity.showToast(uiState.location.locationName)
                                    mainViewModel.fetchLocation()
                                }
                                onToast.collectEffect { msg ->
                                    this@AAAActivity.showToast(msg.asString(this@AAAActivity))
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

    private fun setupEdgeToEdge(themeType: ThemeTypeUiModel?) {
        var isDarkMode: Boolean? = null

        fun isDarkMode(resources: Resources): Boolean {
            isDarkMode?.let { return it }
            isDarkMode = when (themeType) {
                ThemeTypeUiModel.DARK -> true
                ThemeTypeUiModel.AUTO,
                ThemeTypeUiModel.DYNAMIC -> (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

                else -> false
            }
            return isDarkMode
        }

        // Edge to edge
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
                detectDarkMode = ::isDarkMode,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = lightColors().background.toArgb(),
                darkScrim = darkColors().background.toArgb(),
                detectDarkMode = ::isDarkMode,
            ),
        )
    }
}

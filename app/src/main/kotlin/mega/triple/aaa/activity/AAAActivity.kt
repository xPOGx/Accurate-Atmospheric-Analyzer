package mega.triple.aaa.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mega.triple.aaa.common.analytic.AAAAnalytic
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel.Companion.toUiModel
import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.presentation.feature.search.SearchScreen
import mega.triple.aaa.presentation.feature.search.SearchViewModel
import mega.triple.aaa.presentation.navigation.AAANavHost
import mega.triple.aaa.ui.components.loader.GlobalLoading
import mega.triple.aaa.ui.ext.render
import mega.triple.aaa.ui.model.ThemeTypeUiModel
import mega.triple.aaa.ui.theme.AAATheme
import mega.triple.aaa.ui.theme.darkColors
import mega.triple.aaa.ui.theme.lightColors
import javax.inject.Inject

@AndroidEntryPoint
class AAAActivity : ComponentActivity() {
    private val activityStart = System.currentTimeMillis()

    @Inject
    lateinit var settings: SettingsDatastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.lifecycleScope.launch {
            settings.getThemeType().collectLatest { settingTheme ->
                setupEdgeToEdge(settingTheme.toUiModel())
            }
        }

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
            val themeType by mainViewModel.themeTypeUiModel.collectAsStateWithLifecycle(ThemeTypeUiModel.LIGHT)

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

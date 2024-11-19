package mega.triple.aaa.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mega.triple.aaa.presentation.feature.settings.ext.ThemeType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDatastore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val Context.settings: DataStore<Preferences> by preferencesDataStore(SETTINGS_DATASTORE)
    private val preferences = context.settings
    private val data = preferences.data

    fun getThemeType(): Flow<ThemeType> = data.map { pref ->
        val value = pref[themeTypePrefKey]
        ThemeType.entries.firstOrNull { it.type == value } ?: ThemeType.AUTO
    }

    suspend fun setThemeType(type: ThemeType) {
        preferences.edit { pref ->
            pref[themeTypePrefKey] = type.type
        }
    }

    companion object {
        private const val SETTINGS_DATASTORE = "Settings"
        private const val THEME_TYPE_KEY = "ThemeType"

        private val themeTypePrefKey = intPreferencesKey(THEME_TYPE_KEY)
    }
}

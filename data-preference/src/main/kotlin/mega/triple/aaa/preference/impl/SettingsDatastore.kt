package mega.triple.aaa.preference.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.preference.model.ThemeTypePrefModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDatastoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingsDatastore {
    private val Context.settings: DataStore<Preferences> by preferencesDataStore(
        SETTINGS_DATASTORE
    )
    private val preferences = context.settings
    private val data = preferences.data

    override fun getThemeType(): Flow<ThemeTypePrefModel> = data.map { pref ->
        val value = pref[themeTypePrefKey]
        ThemeTypePrefModel.entries.firstOrNull { it.type == value } ?: ThemeTypePrefModel.LIGHT
    }

    override suspend fun setThemeType(type: ThemeTypePrefModel) {
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

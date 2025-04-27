package mega.triple.aaa.preference.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.serialization.json.Json
import mega.triple.aaa.preference.SettingsDatastore
import mega.triple.aaa.preference.model.HomeCardTypePrefModel
import mega.triple.aaa.preference.model.ThemeTypePrefModel

class SettingsDatastoreImpl(
    context: Context,
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

    override fun getLastUpdate(): Flow<Long?> = data.map { pref ->
        pref[lastUpdatePrefKey]
    }

    override suspend fun setLastUpdate(date: Long) {
        preferences.edit { pref ->
            pref[lastUpdatePrefKey] = date
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCardsSetup(): Flow<Map<HomeCardTypePrefModel, Boolean>> = data.mapLatest { pref ->
        val encoded = pref[cardsSetupPrefKey]
        if (encoded.isNullOrEmpty()) {
            emptyMap()
        } else {
            Json.decodeFromString(encoded)
        }
    }

    override suspend fun setCardsSetup(cards: Map<HomeCardTypePrefModel, Boolean>) {
        preferences.edit { pref ->
            pref[cardsSetupPrefKey] = Json.encodeToString(cards)
        }
    }

    companion object {
        private const val SETTINGS_DATASTORE = "SETTINGS_DATASTORE"
        private const val THEME_TYPE_KEY = "THEME_TYPE_KEY"
        private const val LAST_UPDATE_KEY = "LAST_UPDATE_KEY"
        private const val CARDS_SETUP_KEY = "CARDS_SETUP_KEY"

        private val themeTypePrefKey = intPreferencesKey(THEME_TYPE_KEY)
        private val lastUpdatePrefKey = longPreferencesKey(LAST_UPDATE_KEY)
        private val cardsSetupPrefKey = stringPreferencesKey(CARDS_SETUP_KEY)
    }
}

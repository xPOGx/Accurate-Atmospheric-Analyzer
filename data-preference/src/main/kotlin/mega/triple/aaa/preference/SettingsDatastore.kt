package mega.triple.aaa.preference

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.preference.model.ThemeTypePrefModel

interface SettingsDatastore {
    fun getThemeType(): Flow<ThemeTypePrefModel>
    suspend fun setThemeType(type: ThemeTypePrefModel)
    fun getLastUpdate(): Flow<Long?>
    suspend fun setLastUpdate(date: Long)
}

package mega.triple.aaa.domain.pref.theme

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel.Companion.toDomainModel
import mega.triple.aaa.preference.SettingsDatastore
import javax.inject.Inject

class GetThemeUC @Inject constructor(
    private val settingsDatastore: SettingsDatastore,
) {
    operator fun invoke(): Flow<ThemeTypeDomainModel> {
        return try {
            settingsDatastore.getThemeType().map { it.toDomainModel() }
        } catch (e: Exception) {
            flowOf(ThemeTypeDomainModel.LIGHT)
        }
    }
}

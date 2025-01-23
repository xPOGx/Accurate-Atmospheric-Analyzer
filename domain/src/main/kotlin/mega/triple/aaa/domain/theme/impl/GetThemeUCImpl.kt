package mega.triple.aaa.domain.theme.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mega.triple.aaa.domain.theme.GetThemeUC
import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel
import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel.Companion.toDomainModel
import mega.triple.aaa.preference.SettingsDatastore

class GetThemeUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : GetThemeUC {
    override operator fun invoke(): Flow<ThemeTypeDomainModel> {
        return try {
            settingsDatastore.getThemeType().map { it.toDomainModel() }
        } catch (e: Exception) {
            flowOf(ThemeTypeDomainModel.LIGHT)
        }
    }
}

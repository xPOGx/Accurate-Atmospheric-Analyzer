package mega.triple.aaa.domain.pref.theme

import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel
import mega.triple.aaa.domain.pref.model.ThemeTypeDomainModel.Companion.toPrefModel
import mega.triple.aaa.preference.SettingsDatastore
import javax.inject.Inject

class SetThemeUC @Inject constructor(
    private val settingsDatastore: SettingsDatastore,
) {
    suspend operator fun invoke(domainModel: ThemeTypeDomainModel): Result<Unit> {
        return try {
            settingsDatastore.setThemeType(domainModel.toPrefModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

package mega.triple.aaa.domain.theme.impl

import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel
import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel.Companion.toPrefModel
import mega.triple.aaa.domain.theme.SetThemeUC
import mega.triple.aaa.preference.SettingsDatastore
import javax.inject.Inject

class SetThemeUCImpl @Inject constructor(
    private val settingsDatastore: SettingsDatastore,
) : SetThemeUC {
    override suspend operator fun invoke(domainModel: ThemeTypeDomainModel): Result<Unit> {
        return try {
            settingsDatastore.setThemeType(domainModel.toPrefModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

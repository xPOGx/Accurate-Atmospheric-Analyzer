package mega.triple.aaa.domain.settings.impl

import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.settings.SetThemeUC
import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel
import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel.Companion.toPrefModel
import mega.triple.aaa.preference.SettingsDatastore

class SetThemeUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : SetThemeUC {
    override suspend operator fun invoke(domainModel: ThemeTypeDomainModel): Result<Unit> =
        resultLauncher {
            settingsDatastore.setThemeType(domainModel.toPrefModel())
        }
}

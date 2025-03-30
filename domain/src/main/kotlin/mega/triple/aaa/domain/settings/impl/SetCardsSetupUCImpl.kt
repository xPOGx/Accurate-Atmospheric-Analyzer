package mega.triple.aaa.domain.settings.impl

import mega.triple.aaa.domain.settings.SetCardsSetupUC
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel.Companion.toPrefModel
import mega.triple.aaa.preference.SettingsDatastore

class SetCardsSetupUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : SetCardsSetupUC {
    override suspend operator fun invoke(
        domainModels: Map<HomeCardTypeDomainModel, Boolean>
    ): Result<Unit> {
        return try {
            settingsDatastore.setCardsSetup(domainModels.mapKeys { it.key.toPrefModel() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

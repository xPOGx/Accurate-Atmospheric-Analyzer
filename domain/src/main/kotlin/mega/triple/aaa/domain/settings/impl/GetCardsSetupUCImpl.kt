package mega.triple.aaa.domain.settings.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import mega.triple.aaa.domain.settings.GetCardsSetupUC
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel
import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel.Companion.toDomainModel
import mega.triple.aaa.preference.SettingsDatastore

class GetCardsSetupUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : GetCardsSetupUC {
    override operator fun invoke(): Flow<Map<HomeCardTypeDomainModel, Boolean>> {
        return try {
            settingsDatastore.getCardsSetup().map { map ->
                map.mapKeys { it.key.toDomainModel() }
            }
        } catch (_: Throwable) {
            flowOf(emptyMap())
        }
    }
}

package mega.triple.aaa.domain.settings.impl

import mega.triple.aaa.domain.ext.resultLauncher
import mega.triple.aaa.domain.settings.SetLastUpdateUC
import mega.triple.aaa.preference.SettingsDatastore

class SetLastUpdateUCImpl(
    private val settingsDatastore: SettingsDatastore,
) : SetLastUpdateUC {
    override suspend operator fun invoke(date: Long): Result<Unit> =
        resultLauncher {
            settingsDatastore.setLastUpdate(date)
        }
}

package mega.triple.aaa.domain.settings

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel

interface GetThemeUC {
    operator fun invoke(): Flow<ThemeTypeDomainModel>
}

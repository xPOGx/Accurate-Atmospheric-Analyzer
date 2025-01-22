package mega.triple.aaa.domain.theme

import kotlinx.coroutines.flow.Flow
import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel

interface GetThemeUC {
    operator fun invoke(): Flow<ThemeTypeDomainModel>
}

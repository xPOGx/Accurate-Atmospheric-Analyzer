package mega.triple.aaa.domain.theme

import mega.triple.aaa.domain.theme.model.ThemeTypeDomainModel

interface SetThemeUC {
    suspend operator fun invoke(domainModel: ThemeTypeDomainModel): Result<Unit>
}

package mega.triple.aaa.domain.settings

import mega.triple.aaa.domain.settings.model.ThemeTypeDomainModel

interface SetThemeUC {
    suspend operator fun invoke(domainModel: ThemeTypeDomainModel): Result<Unit>
}

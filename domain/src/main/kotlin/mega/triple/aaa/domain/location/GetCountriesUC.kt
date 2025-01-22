package mega.triple.aaa.domain.location

import mega.triple.aaa.domain.location.model.CountryDomainModel

interface GetCountriesUC {
    suspend operator fun invoke(continentId: String): Result<List<CountryDomainModel>>
}

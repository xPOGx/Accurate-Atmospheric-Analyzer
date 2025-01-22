package mega.triple.aaa.domain.location

import mega.triple.aaa.domain.location.model.CityDomainModel

interface GetCitiesUC {
    suspend operator fun invoke(
        continentId: String,
        countryId: String,
    ): Result<List<CityDomainModel>>
}

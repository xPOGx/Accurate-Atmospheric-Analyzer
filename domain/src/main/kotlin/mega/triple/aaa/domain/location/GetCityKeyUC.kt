package mega.triple.aaa.domain.location

interface GetCityKeyUC {
    suspend operator fun invoke(
        countryId: String,
        cityId: String,
        cityName: String,
    ): Result<String>
}

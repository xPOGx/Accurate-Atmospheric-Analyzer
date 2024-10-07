package mega.triple.aaa.data.network

object NetworkHelper {
    private const val BASE_URL = "https://dataservice.accuweather.com"

    const val LOCATION_URL = "$BASE_URL/locations/v1"
    const val FORECAST_URL = "$BASE_URL/forecasts/v1"

    const val PARAM_API_KEY = "apikey"
    const val PARAM_LANGUAGE = "language"
    const val PARAM_QUERY = "q"
    const val PARAM_DETAILS = "details"
    const val PARAM_METRIC = "metric"

    const val DEFAULT_LANGUAGE = "uk-ua"
    const val DEFAULT_METRIC = true
}
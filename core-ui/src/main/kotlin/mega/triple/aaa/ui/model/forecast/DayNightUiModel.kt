package mega.triple.aaa.ui.model.forecast


data class DayNightUiModel(
    val cloudCover: Int?,
    val evapotranspiration: ValueUiModel?,
    val hasPrecipitation: Boolean?,
    val hoursOfIce: Double?,
    val hoursOfPrecipitation: Double?,
    val hoursOfRain: Double?,
    val hoursOfSnow: Double?,
    val ice: ValueUiModel?,
    val iceProbability: Int?,
    val icon: Int?,
    val iconPhrase: String?,
    val longPhrase: String?,
    val precipitationProbability: Int?,
    val rain: ValueUiModel?,
    val rainProbability: Int?,
    val relativeHumidity: RelativeHumidityUiModel?,
    val shortPhrase: String?,
    val snow: ValueUiModel?,
    val snowProbability: Int?,
    val solarIrradiance: ValueUiModel?,
    val thunderstormProbability: Int?,
    val totalLiquid: ValueUiModel?,
    val wetBulbGlobeTemperature: ValueWrapperUiModel?,
    val wetBulbTemperature: ValueWrapperUiModel?,
    val wind: DirectionWrapperUiModel?,
    val windGust: DirectionWrapperUiModel?
)

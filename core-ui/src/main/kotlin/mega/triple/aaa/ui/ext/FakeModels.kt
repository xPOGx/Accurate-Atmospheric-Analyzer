package mega.triple.aaa.ui.ext

import mega.triple.aaa.ui.model.forecast.CategoryUiModel
import mega.triple.aaa.ui.model.forecast.DailyForecastUiModel
import mega.triple.aaa.ui.model.forecast.DayNightUiModel
import mega.triple.aaa.ui.model.forecast.DirectionUiModel
import mega.triple.aaa.ui.model.forecast.DirectionWrapperUiModel
import mega.triple.aaa.ui.model.forecast.RelativeHumidityUiModel
import mega.triple.aaa.ui.model.forecast.SunMoonUiModel
import mega.triple.aaa.ui.model.forecast.ValueUiModel
import mega.triple.aaa.ui.model.forecast.ValueWrapperUiModel

val categoryUiModel = CategoryUiModel(
    category = "expetendis",
    categoryValue = 6371,
    name = "Alfredo Fulton",
    type = "elaboraret",
    value = 8435
)

val sunMoonUiModel = SunMoonUiModel(
    age = 1733,
    epochRise = 8599,
    epochSet = 9771,
    phase = "lorem",
    timeRise = "eu",
    timeSet = "dissentiunt"
)

val valueUiModel = ValueUiModel(
    phrase = "verear",
    unit = "dignissim",
    unitType = 8762,
    value = 184.185
)

val directionUiModel = DirectionUiModel(
    degrees = 4632,
    english = "unum",
    localized = "mus",
)

val relativeHumidityUiModel = RelativeHumidityUiModel(
    average = 2791,
    maximum = 9983,
    minimum = 9523,
)

val valueWrapperUiModel = ValueWrapperUiModel(
    average = valueUiModel,
    maximum = valueUiModel,
    minimum = valueUiModel,
)

val directionWrapperUiModel = DirectionWrapperUiModel(
    direction = directionUiModel,
    speed = valueUiModel,
)

val dayNightUiModel = DayNightUiModel(
    icon = 0,
    iconPhrase = "iconPhrase",
    hasPrecipitation = false,
    shortPhrase = "shortPhrase",
    longPhrase = "longPhrase",
    rainProbability = 0,
    snowProbability = 0,
    iceProbability = 0,
    thunderstormProbability = 0,
    windGust = directionWrapperUiModel,
    wind = directionWrapperUiModel,
    totalLiquid = valueUiModel,
    rain = valueUiModel,
    snow = valueUiModel,
    ice = valueUiModel,
    cloudCover = 4880,
    evapotranspiration = valueUiModel,
    hoursOfIce = 174.175,
    hoursOfPrecipitation = 176.177,
    hoursOfRain = 178.179,
    hoursOfSnow = 180.181,
    precipitationProbability = 6868,
    relativeHumidity = relativeHumidityUiModel,
    solarIrradiance = valueUiModel,
    wetBulbGlobeTemperature = valueWrapperUiModel,
    wetBulbTemperature = valueWrapperUiModel,
)

val dailyForecastUiModel = DailyForecastUiModel(
    airAndPollen = listOf(categoryUiModel),
    date = "enim",
    day = dayNightUiModel,
    epochDate = 2531,
    hoursOfSun = 4.5,
    moon = sunMoonUiModel,
    night = dayNightUiModel,
    realFeelTemperature = valueWrapperUiModel,
    realFeelTemperatureShade = valueWrapperUiModel,
    sun = sunMoonUiModel,
    temperature = valueWrapperUiModel
)
package mega.triple.aaa.domain.settings.model

import mega.triple.aaa.preference.model.HomeCardTypePrefModel

enum class HomeCardTypeDomainModel {
    WIND_SPEED,
    RAIN_CHANCE,
    AIR_QUALITY,
    UV_INDEX,
    FORECAST_HOURLY,
    FORECAST_DAILY,
    FORECAST_RAIN_CHANCE,
    SUN_RISE,
    SUN_SET,
    MOON_RISE,
    ;

    companion object {
        fun HomeCardTypeDomainModel.toPrefModel() = when (this) {
            WIND_SPEED -> HomeCardTypePrefModel.WIND_SPEED
            RAIN_CHANCE -> HomeCardTypePrefModel.RAIN_CHANCE
            AIR_QUALITY -> HomeCardTypePrefModel.AIR_QUALITY
            UV_INDEX -> HomeCardTypePrefModel.UV_INDEX
            FORECAST_HOURLY -> HomeCardTypePrefModel.FORECAST_HOURLY
            FORECAST_DAILY -> HomeCardTypePrefModel.FORECAST_DAILY
            FORECAST_RAIN_CHANCE -> HomeCardTypePrefModel.FORECAST_RAIN_CHANCE
            SUN_RISE -> HomeCardTypePrefModel.SUN_RISE
            SUN_SET -> HomeCardTypePrefModel.SUN_SET
            MOON_RISE -> HomeCardTypePrefModel.MOON_RISE
        }

        fun HomeCardTypePrefModel.toDomainModel() = when (this) {
            HomeCardTypePrefModel.WIND_SPEED -> WIND_SPEED
            HomeCardTypePrefModel.RAIN_CHANCE -> RAIN_CHANCE
            HomeCardTypePrefModel.AIR_QUALITY -> AIR_QUALITY
            HomeCardTypePrefModel.UV_INDEX -> UV_INDEX
            HomeCardTypePrefModel.FORECAST_HOURLY -> FORECAST_HOURLY
            HomeCardTypePrefModel.FORECAST_DAILY -> FORECAST_DAILY
            HomeCardTypePrefModel.FORECAST_RAIN_CHANCE -> FORECAST_RAIN_CHANCE
            HomeCardTypePrefModel.SUN_RISE -> SUN_RISE
            HomeCardTypePrefModel.SUN_SET -> SUN_SET
            HomeCardTypePrefModel.MOON_RISE -> MOON_RISE
        }
    }
}

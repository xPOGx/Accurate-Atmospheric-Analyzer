package mega.triple.aaa.home.ext

import mega.triple.aaa.domain.settings.model.HomeCardTypeDomainModel

enum class HomeCardType {
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
        fun HomeCardTypeDomainModel.toUiModel() = when(this) {
            HomeCardTypeDomainModel.WIND_SPEED -> WIND_SPEED
            HomeCardTypeDomainModel.RAIN_CHANCE -> RAIN_CHANCE
            HomeCardTypeDomainModel.AIR_QUALITY -> AIR_QUALITY
            HomeCardTypeDomainModel.UV_INDEX -> UV_INDEX
            HomeCardTypeDomainModel.FORECAST_HOURLY -> FORECAST_HOURLY
            HomeCardTypeDomainModel.FORECAST_DAILY -> FORECAST_DAILY
            HomeCardTypeDomainModel.FORECAST_RAIN_CHANCE -> FORECAST_RAIN_CHANCE
            HomeCardTypeDomainModel.SUN_RISE -> SUN_RISE
            HomeCardTypeDomainModel.SUN_SET -> SUN_SET
            HomeCardTypeDomainModel.MOON_RISE -> MOON_RISE
        }

        fun HomeCardType.toDomainModel() = when(this) {
            WIND_SPEED -> HomeCardTypeDomainModel.WIND_SPEED
            RAIN_CHANCE -> HomeCardTypeDomainModel.RAIN_CHANCE
            AIR_QUALITY -> HomeCardTypeDomainModel.AIR_QUALITY
            UV_INDEX -> HomeCardTypeDomainModel.UV_INDEX
            FORECAST_HOURLY -> HomeCardTypeDomainModel.FORECAST_HOURLY
            FORECAST_DAILY -> HomeCardTypeDomainModel.FORECAST_DAILY
            FORECAST_RAIN_CHANCE -> HomeCardTypeDomainModel.FORECAST_RAIN_CHANCE
            SUN_RISE -> HomeCardTypeDomainModel.SUN_RISE
            SUN_SET -> HomeCardTypeDomainModel.SUN_SET
            MOON_RISE -> HomeCardTypeDomainModel.MOON_RISE
        }
    }
}

package mega.triple.aaa.data.local.model.forecast.daily

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import mega.triple.aaa.data.local.converters.CategoryDbModelListConverter
import mega.triple.aaa.data.local.model.forecast.CategoryDbModel
import mega.triple.aaa.data.local.model.forecast.DayNightDbModel
import mega.triple.aaa.data.local.model.forecast.SunMoonDbModel
import mega.triple.aaa.data.local.model.forecast.ValueWrapperDbModel

@Entity(
    tableName = "daily_forecast",
)
@TypeConverters(CategoryDbModelListConverter::class)
data class DailyForecastDbModel(
    @ColumnInfo("air_pollen")
    val airAndPollen: List<CategoryDbModel>?,
    @ColumnInfo("date")
    val date: String?,
    @Embedded("day")
    val day: DayNightDbModel?,
    @PrimaryKey
    @ColumnInfo("epoch_date")
    val epochDate: Long,
    @ColumnInfo("hours_of_sun")
    val hoursOfSun: Double?,
    @Embedded("moon")
    val moonResponse: SunMoonDbModel?,
    @Embedded("night")
    val night: DayNightDbModel?,
    @Embedded("real_feel_temperature")
    val realFeelTemperature: ValueWrapperDbModel?,
    @Embedded("real_feel_temperature_shade")
    val realFeelTemperatureShade: ValueWrapperDbModel?,
    @Embedded("sun")
    val sun: SunMoonDbModel?,
    @Embedded("temperature")
    val temperature: ValueWrapperDbModel?,
)
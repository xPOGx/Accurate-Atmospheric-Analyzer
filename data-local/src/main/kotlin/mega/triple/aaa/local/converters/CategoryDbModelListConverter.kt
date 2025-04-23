package mega.triple.aaa.local.converters

import androidx.room.TypeConverter
import io.ktor.serialization.kotlinx.json.DefaultJson
import mega.triple.aaa.local.model.forecast.CategoryDbModel

class CategoryDbModelListConverter {
    @TypeConverter
    fun fromCategories(categories: List<CategoryDbModel>?): String? {
        if (categories.isNullOrEmpty()) {
            return null
        }
        return DefaultJson.encodeToString(categories)
    }

    @TypeConverter
    fun toCategories(data: String?): List<CategoryDbModel>? {
        if (data.isNullOrEmpty()) {
            return null
        }
        return DefaultJson.decodeFromString(data)
    }
}

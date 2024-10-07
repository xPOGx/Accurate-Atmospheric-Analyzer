package mega.triple.aaa.data.local.converters

import androidx.room.TypeConverter
import io.ktor.serialization.kotlinx.json.DefaultJson
import kotlinx.serialization.encodeToString
import mega.triple.aaa.data.local.model.forecast.CategoryDbModel

class CategoryDbModelListConverter {
    @TypeConverter
    fun fromCategories(categories: List<CategoryDbModel>?) : String? {
        if (categories.isNullOrEmpty()) {
            return null
        }
        return DefaultJson.encodeToString(categories) // Use Gson or any other JSON library
    }

    @TypeConverter
    fun toCategories(data: String?): List<CategoryDbModel>? {
        if (data.isNullOrEmpty()) {
            return null
        }
        return DefaultJson.decodeFromString(data)
    }
}
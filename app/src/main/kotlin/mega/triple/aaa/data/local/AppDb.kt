package mega.triple.aaa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mega.triple.aaa.data.local.dao.CityDao
import mega.triple.aaa.data.local.dao.ContinentDao
import mega.triple.aaa.data.local.dao.CountryDao
import mega.triple.aaa.data.local.model.CityDbModel
import mega.triple.aaa.data.local.model.ContinentDbModel
import mega.triple.aaa.data.local.model.CountryDbModel

const val DB_VERSION = 1

@Database(
    version = DB_VERSION,
    entities = [
        ContinentDbModel::class,
        CountryDbModel::class,
        CityDbModel::class,
    ],
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun continentDao(): ContinentDao
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            return instance ?: synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app_database",
                )
                    .build()
                    .also { instance = it }
            }
        }
    }
}
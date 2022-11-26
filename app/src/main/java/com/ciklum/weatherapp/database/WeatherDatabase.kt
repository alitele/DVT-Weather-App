package dk.zibralabs.m2call.migo.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ciklum.weatherapp.database.daos.FavouriteDao
import com.ciklum.weatherapp.database.daos.WeatherDao
import com.ciklum.weatherapp.features.favourites.model.Favourite
import java.util.concurrent.Executors

@Database(entities = [WeatherDao::class, Favourite::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun favDao(): FavouriteDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    val dbBuilder =
                        Room.databaseBuilder(
                            context,
                            WeatherDatabase::class.java,
                            "weatherDatabase"
                        )
                    dbBuilder.setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        println("SQLQuery: $sqlQuery SQL Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    INSTANCE = dbBuilder.build()
                }
            }
            return INSTANCE
        }
    }
}
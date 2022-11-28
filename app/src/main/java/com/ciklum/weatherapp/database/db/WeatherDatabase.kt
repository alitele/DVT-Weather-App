package com.ciklum.weatherapp.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ciklum.weatherapp.database.converts.Converters
import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.database.entities.LocationEntity

@Database(entities = [LocationEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: LocationDao
}
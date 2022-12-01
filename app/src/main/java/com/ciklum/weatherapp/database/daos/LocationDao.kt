package com.ciklum.weatherapp.database.daos

import androidx.room.*
import com.ciklum.weatherapp.database.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity)

    @Update
    suspend fun updateLocation(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLocation(locationEntity: LocationEntity)

    @Delete
    suspend fun deleteLocation(locationEntity: LocationEntity)

    @Query("DELETE FROM Location WHERE isMyLocation = 1")
    suspend fun deleteCurrLocations()

    @Query("SELECT * FROM Location")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM Location WHERE id = :id")
    fun getSingleLocation(id: Int): LocationEntity

    @Query("SELECT * FROM Location WHERE id = :id")
    fun getSingleNonObserverLocation(id: Int): LocationEntity

    @Query("SELECT * FROM Location WHERE isMyLocation = 1")
    fun getCurrentLocation(): LocationEntity

}
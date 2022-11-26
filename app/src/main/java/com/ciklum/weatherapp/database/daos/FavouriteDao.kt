package com.ciklum.weatherapp.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ciklum.weatherapp.database.entities.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBed(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteBed(favourite: FavouriteEntity)

    @Query("SELECT * FROM Favourite")
    fun getBeds(): LiveData<List<FavouriteEntity>>

    @Query("SELECT * FROM Favourite WHERE id = :id")
    fun getSingleBed(id: Int): LiveData<FavouriteEntity>

}
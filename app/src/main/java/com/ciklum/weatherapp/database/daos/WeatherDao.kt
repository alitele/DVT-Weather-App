package com.ciklum.weatherapp.database.daos

import androidx.room.*

@Dao
interface WeatherDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertBed(bed: Bed)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(bed: List<Bed>)
//
//    @Update
//    suspend fun updateBed(bed: Bed)
//
//    @Delete
//    suspend fun deleteBed(bed: Bed)
//
//    @Query("SELECT * FROM Bed")
//    fun getBeds(): LiveData<List<Bed>>
//
//    @Query("SELECT * FROM Bed")
//    suspend fun getBedsNonObserver(): List<Bed>
//
//    @Query("SELECT * FROM Bed WHERE bedID = :bedID")
//    fun getSingleBed(bedID: Int): LiveData<Bed>
//
//    @Query("SELECT * FROM Bed WHERE bedID = :bedID")
//    fun getSingleBedNonObserver(bedID: Int): Bed
//
//    @Query("""UPDATE Bed SET isSelected = 0 AND category="" AND pauseNotificationID=-1 AND pauseTimeRemaining=0 AND totalPauseTime=0 AND isPause=0""")
//    fun freeAllBeds()
//
//    @Query("DELETE FROM Bed")
//    suspend fun deleteAll()

}
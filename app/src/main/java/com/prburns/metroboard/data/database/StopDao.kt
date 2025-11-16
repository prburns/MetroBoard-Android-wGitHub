package com.prburns.metroboard.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StopDao {
    @Query("SELECT * FROM stops")
    fun getAllStops(): Flow<List<StopEntity>>

    @Query("SELECT * FROM stops WHERE stopId = :stopId")
    suspend fun getStopById(stopId: String): StopEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStop(stop: StopEntity)

    @Update
    suspend fun updateStop(stop: StopEntity)

    @Delete
    suspend fun deleteStop(stop: StopEntity)

    @Query("DELETE FROM stops")
    suspend fun deleteAllStops()
}

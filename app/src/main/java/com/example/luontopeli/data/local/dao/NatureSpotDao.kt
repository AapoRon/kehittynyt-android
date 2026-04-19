package com.example.luontopeli.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.luontopeli.data.local.entity.NatureSpot
import kotlinx.coroutines.flow.Flow

@Dao
interface NatureSpotDao {
    @Query("SELECT * FROM nature_spots ORDER BY timestamp DESC")
    fun getAllSpots(): Flow<List<NatureSpot>>

    @Query("SELECT * FROM nature_spots WHERE latitude != 0.0 AND longitude != 0.0")
    fun getSpotsWithLocation(): Flow<List<NatureSpot>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spot: NatureSpot)

    @Delete
    suspend fun delete(spot: NatureSpot)

    @Query("SELECT * FROM nature_spots WHERE synced = 0")
    suspend fun getUnsyncedSpots(): List<NatureSpot>

    @Query("UPDATE nature_spots SET synced = 1, imageFirebaseUrl = :imageUrl WHERE id = :spotId")
    suspend fun markSynced(spotId: String, imageUrl: String)
}

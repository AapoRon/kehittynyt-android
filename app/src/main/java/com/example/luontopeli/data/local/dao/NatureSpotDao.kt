package com.example.luontopeli.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.luontopeli.data.local.entity.NatureSpot
import kotlinx.coroutines.flow.Flow

@Dao
interface NatureSpotDao {
    @Query("SELECT * FROM nature_spots ORDER BY timestamp DESC")
    fun getAllSpots(): Flow<List<NatureSpot>>

    @Insert
    suspend fun insert(spot: NatureSpot)
}

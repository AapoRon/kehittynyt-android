package com.example.luontopeli.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nature_spots")
data class NatureSpot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val plantLabel: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)

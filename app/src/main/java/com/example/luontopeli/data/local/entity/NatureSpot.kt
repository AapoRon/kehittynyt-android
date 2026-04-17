package com.example.luontopeli.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nature_spots")
data class NatureSpot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

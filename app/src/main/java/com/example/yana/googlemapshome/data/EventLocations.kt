package com.example.yana.googlemapshome.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class EventLocations(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val locations: List<LatLng>,
    var startTime: Long = 0L,
    var endTime: Long = 0L,
    var totalDistance: Int = 0,
    var timesInSeconds: Long = 0L,
    var speed: Double = 0.0
)

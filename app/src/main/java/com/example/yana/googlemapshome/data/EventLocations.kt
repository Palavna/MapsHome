package com.example.yana.googlemapshome.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class EventLocations(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val locations: List<LatLng>
)

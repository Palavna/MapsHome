package com.example.yana.googlemapshome.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MapsInterface {
    @Insert
    fun saveMap(location: EventLocations)

    @Query("SELECT * FROM EventLocations")
    fun getAllMap(): List<EventLocations>
}
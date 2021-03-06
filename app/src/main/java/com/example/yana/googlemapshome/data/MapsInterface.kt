package com.example.yana.googlemapshome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MapsInterface {
    @Insert
    suspend fun saveMap(location: EventLocations)

    @Query("SELECT * FROM EventLocations")
    fun getAllMap(): List<EventLocations>

    @Query("SELECT * FROM EventLocations order by id desc limit 5")
//    TODO asc
    fun getAllMapLiveData(): LiveData<List<EventLocations>>
}
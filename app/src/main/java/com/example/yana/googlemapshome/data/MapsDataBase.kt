package com.example.yana.googlemapshome.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yana.googlemapshome.utils.MapsTapeConv

@Database(entities = [EventLocations::class], version = 1)
@TypeConverters(MapsTapeConv::class)
abstract class MapsDataBase: RoomDatabase() {
    abstract fun getUserMap(): MapsInterface
}
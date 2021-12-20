package com.example.yana.googlemapshome.view

import android.app.Application
import androidx.room.Room
import com.example.yana.googlemapshome.data.MapsDataBase
import com.example.yana.googlemapshome.di.mapsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MapsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MapsApplication)
            mapsModules
        }
        DB = Room.databaseBuilder(this, MapsDataBase::class.java, "DB")
            .build()
    }

    companion object{
        var DB: MapsDataBase? = null
    }
}
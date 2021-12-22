package com.example.yana.googlemapshome.di

import androidx.room.Room
import com.example.yana.googlemapshome.data.MapsDataBase
import com.example.yana.googlemapshome.ui.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


val mapsModules by lazy {
    loadKoinModules(
        listOf(
            viewModelModules,
            dbModule
        )
    )
}

val viewModelModules = module {
    viewModel { MapsViewModel(get()) }
}

val dbModule = module {
    single { Room.databaseBuilder(get(), MapsDataBase::class.java, "weather")
        .build()
    }
    single { get<MapsDataBase>().getUserMap()}
}
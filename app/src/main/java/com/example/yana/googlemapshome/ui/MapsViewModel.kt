package com.example.yana.googlemapshome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yana.googlemapshome.data.EventLocations
import com.example.yana.googlemapshome.data.MapsAdapter
import com.example.yana.googlemapshome.data.MapsInterface
import com.example.yana.googlemapshome.view.MapsApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MapsViewModel(private val mapsDao: MapsInterface): ViewModel() {

    private val adapter = MapsAdapter()
    private val viewModelScope = CoroutineScope(Job())
    val points: LiveData<List<EventLocations>>
    get() = mapsDao.getAllMapLiveData()

    fun savePoints(data: EventLocations) {
//        TODO Dispatchers.Default - internet, Dispatchers.IO - DataBase(ROOM), Dispatchers.Main - mainThread
        viewModelScope.launch {
            mapsDao.saveMap(data)
            val list = MapsApplication.DB?.getUserMap()?.getAllMap()
            adapter.addAll(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
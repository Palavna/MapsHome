package com.example.yana.googlemapshome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.yana.googlemapshome.data.EventLocations
import com.example.yana.googlemapshome.data.MapsAdapter
import com.example.yana.googlemapshome.data.MapsInterface
import com.example.yana.googlemapshome.view.MapsApplication
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MapsViewModel(private val mapsDao: MapsInterface) : ViewModel() {

    private val viewModelScope = CoroutineScope(Job())
    val points: LiveData<List<EventLocations>>
        get() = mapsDao.getAllMapLiveData()

    fun savePoints(data: EventLocations) {
//        TODO Dispatchers.Default - internet, Dispatchers.IO - DataBase(ROOM), Dispatchers.Main - mainThread
        viewModelScope.launch {
            data.totalDistance = calculateTotalDistance(data.locations)
            data.timesInSeconds = (data.endTime - data.startTime) / 1000
            data.speed = (data.totalDistance.toDouble() / 1000) / (data.timesInSeconds.toDouble() / 3600)
            mapsDao.saveMap(data)
        }
    }

    private fun calculateTotalDistance(locations: List<LatLng>): Int {
        var lastLatLng: LatLng? = null
        var total: Long = 0

        locations.forEach {location ->
            if (lastLatLng!= null)
                total += SphericalUtil.computeDistanceBetween(lastLatLng, location).toLong()
            lastLatLng = location
        }
        return total.toInt()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
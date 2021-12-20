package com.example.yana.googlemapshome.utils

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MapsTapeConv {
    @TypeConverter
    fun fromStringList(list: List<LatLng>?): String? {
        return if (list == null) {
            null
        } else Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(string: String?): List<LatLng?>? {
        val devicesType = object :
            TypeToken<List<LatLng?>?>() {}.type
        return Gson()
            .fromJson<List<LatLng?>>(string, devicesType)
    }
}
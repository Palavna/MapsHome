package com.example.yana.googlemapshome.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object PermissionUtil {
    const val LOCATION_REQUEST_CODE = 111


    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    fun checkLocationPermission(activity: AppCompatActivity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                permissions[0]
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                permissions[1]
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true
        getPermission(activity)
        return false
    }

    private fun getPermission(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, permissions, LOCATION_REQUEST_CODE)
    }
}
package com.example.yana.googlemapshome.data

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.utils.NotificationUtils
import com.google.android.gms.location.*
import java.nio.file.attribute.AclEntry

class SimpleService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private var fusedLocation: FusedLocationProviderClient? = null
    private var fusedCallback: LocationCallback? = null
    private var fusedRequest: LocationRequest? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ServiceAction.STOP.name){
            stopForeground(true)
            stopSelf()
        }else if (intent?.action == ServiceAction.START.name){
            createNotification()
        }

        return START_STICKY
    }

    private val mNotificationId = 123

    private fun createNotification(){
       startForeground(mNotificationId, NotificationUtils.createNotification(applicationContext))
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        fusedRequest = LocationRequest.create()
        fusedRequest?.fastestInterval = 5_000
        fusedRequest?.interval = 10_000
        fusedRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedRequest?.smallestDisplacement = 10F

        fusedCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                Log.d("aaaaaaaaaaaa", p0?.lastLocation?.latitude.toString())
            }
        }
        fusedLocation?.requestLocationUpdates(fusedRequest, fusedCallback, Looper.getMainLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocation?.removeLocationUpdates(fusedCallback)
    }
}


enum class ServiceAction{
    STOP, START
}
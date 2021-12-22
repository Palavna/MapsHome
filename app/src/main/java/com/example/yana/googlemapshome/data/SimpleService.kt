package com.example.yana.googlemapshome.data

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.utils.NotificationUtils
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import org.greenrobot.eventbus.EventBus
import java.nio.file.attribute.AclEntry

class SimpleService: Service() {

    override fun onBind(p0: Intent?): IBinder {
        return SimpleServiceBinder()
    }

    inner class SimpleServiceBinder: Binder(){
        val service: SimpleService
        get() = this@SimpleService
    }

    private var fusedLocation: FusedLocationProviderClient? = null
    private var fusedCallback: LocationCallback? = null
    private var fusedRequest: LocationRequest? = null
    val point = MutableLiveData<EventLocations>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ServiceAction.STOP.name){
            stopForeground(true)
            stopSelf()
        }else if (intent?.action == ServiceAction.START.name){
            createNotification()
        }

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private val mNotificationId = 123

    private fun createNotification(){
       startForeground(mNotificationId, NotificationUtils.createNotification(applicationContext))
        startLocationUpdates()
    }

    private val latLngList = arrayListOf<LatLng>()

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

                if (p0?.lastLocation != null) {
                    latLngList.add(LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude))
                }
                Log.d("aaaaaaaaaaaa", latLngList.size.toString())
            }
        }
        fusedLocation?.requestLocationUpdates(fusedRequest, fusedCallback, Looper.getMainLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocation?.removeLocationUpdates(fusedCallback)
        point.postValue(EventLocations(locations = latLngList))
//        EventBus.getDefault().post(EventLocations(locations = latLngList))
    }
}


enum class ServiceAction{
    STOP, START
}
package com.example.yana.googlemapshome.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.data.ServiceAction
import com.example.yana.googlemapshome.data.SimpleService
import com.example.yana.googlemapshome.databinding.ActivityMapsBinding
import com.example.yana.googlemapshome.utils.PermissionUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var polylineOptions: PolylineOptions? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            val intent = Intent(this, SimpleService::class.java)
            intent.action = ServiceAction.START.name
            startService(intent)
//            NotificationUtils.showNotification(this)
        }
        binding.stop.setOnClickListener {
            val intent = Intent(this, SimpleService::class.java)
            intent.action = ServiceAction.STOP.name
            startService(intent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        polylineOptions = PolylineOptions()
        mMap.addPolyline(polylineOptions!!)

        mMap.setOnMapClickListener {
            createLine(it)
        }

        mMap.setMinZoomPreference(6f)
        mMap.setMaxZoomPreference(13f)

        val bishkek = LatLng(0.0, 0.0)
        mMap.addMarker(MarkerOptions().position(bishkek).title("bishkek"))
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        enableMyLocation()

        if (PermissionUtil.checkLocationPermission(this))
            enableMyLocation()

        fun polyline() {
            val polylineOptions = PolylineOptions()
                .add(LatLng(37.35, -122.0))
                .add(LatLng(37.45, -122.0))
                .add(LatLng(37.45, -122.2))
                .add(LatLng(37.35, -122.2))
                .add(LatLng(37.35, -122.0))
            mMap.addPolyline(polylineOptions)
        }

        fun polygon() {
            val hole = listOf(
                LatLng(1.0, 1.0),
                LatLng(1.0, 2.0),
                LatLng(2.0, 2.0),
                LatLng(2.0, 1.0),
                LatLng(1.0, 1.0)
            )

            val polygon2 = mMap.addPolygon(
                PolygonOptions()
                    .add(
                        LatLng(0.0, 0.0),
                        LatLng(0.0, 5.0),
                        LatLng(3.0, 5.0)
                    )
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE)
                    .addHole(hole)
            )
        }

        fun circle() {
            val circleOption = CircleOptions()
                .center(LatLng(37.4, -122.1))
                .radius(1000.0)
                .fillColor(Color.CYAN)
                .strokeColor(Color.BLACK)
            val circle = mMap.addCircle(circleOption)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.4, -122.1), 8f))
        }

        circle()
        polygon()
        polyline()
    }

    fun createLine(latLng: LatLng) {
        polylineOptions?.add(latLng)
        mMap.addPolyline(polylineOptions!!)
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (REQUEST_CODE == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                enableMyLocation()
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}
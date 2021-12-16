package com.example.yana.googlemapshome.ui

import android.content.Intent
import android.os.Bundle
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.data.ServiceAction
import com.example.yana.googlemapshome.data.SimpleService
import com.example.yana.googlemapshome.databinding.ActivityMapsBinding

class MapsActivity : BaseMapActivity() {

    private lateinit var binding: ActivityMapsBinding
    override val mapId: Int = R.id.map


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
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}
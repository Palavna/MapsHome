package com.example.yana.googlemapshome.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.data.EventLocations
import com.example.yana.googlemapshome.data.MapsAdapter
import com.example.yana.googlemapshome.data.ServiceAction
import com.example.yana.googlemapshome.data.SimpleService
import com.example.yana.googlemapshome.databinding.ActivityMapsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MapsActivity : BaseMapActivity() {

    private lateinit var binding: ActivityMapsBinding
    override val mapId: Int = R.id.map
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: MapsViewModel by viewModel()
    private var isBound = false
    private var myService: SimpleService? = null
    private val adapter = MapsAdapter()

    var startTime = 0L
    val time = object: CountDownTimer(Long.MAX_VALUE, 1000L){
        override fun onTick(millisUntilFinished: Long) {
            val currentTime = System.currentTimeMillis()
            val res = currentTime - startTime
            val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
            binding.includedBottomSheet.tvTimer.text = sdf.format(res)
            Log.d("dfgsdfg", res.toString())
        }
        override fun onFinish() {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sheetBehavior = BottomSheetBehavior.from(binding.includedBottomSheet.bottomSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        binding.includedBottomSheet.recyclerMap.adapter = adapter

        binding.stop.setOnClickListener {
            if (isBound){
                val intent = Intent(this, SimpleService::class.java)
                intent.action = ServiceAction.STOP.name
                unbindService(serviceConnection)
                isBound = false
                time.cancel()
            }
        }

        binding.includedBottomSheet.btnStart.setOnClickListener {
            bindService()
            startTime = System.currentTimeMillis()
            time.start()
        }


        viewModel.points.observe(this, {
            adapter.addAll(it)
        })

        binding.includedBottomSheet.topBar.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                binding.includedBottomSheet.image.alpha = slideOffset
                binding.includedBottomSheet.btnStart.alpha = slideOffset
                binding.includedBottomSheet.line.alpha = 1 - slideOffset
            }

        })
    }

    private fun bindService() {
        val intent = Intent(this, SimpleService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
//        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private val serviceConnection = object: ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as SimpleService.SimpleServiceBinder
            myService = binder.service
            isBound = true
            myService?.point?.observe(this@MapsActivity,{
                eventLocations(it)
            })
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
           isBound = false
        }
    }

    fun eventLocations(data: EventLocations){
        val endTime = System.currentTimeMillis()
        data.startTime = startTime
        data.endTime = endTime
        viewModel.savePoints(data)
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}
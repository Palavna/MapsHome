package com.example.yana.googlemapshome.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yana.googlemapshome.R
import com.example.yana.googlemapshome.data.ServiceAction
import com.example.yana.googlemapshome.data.SimpleService
import com.example.yana.googlemapshome.databinding.ActivityMapsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*

class MapsActivity : BaseMapActivity() {

    private lateinit var binding: ActivityMapsBinding
    override val mapId: Int = R.id.map
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

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




        binding.includedBottomSheet.btnStart.setOnClickListener {
            startTime = System.currentTimeMillis()
            time.start()
        }



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
        binding.includedBottomSheet.topBar.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.includedBottomSheet.image.alpha = slideOffset
                binding.includedBottomSheet.btnStart.alpha = slideOffset
                binding.includedBottomSheet.line.alpha = 1 - slideOffset
            }

        })
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}
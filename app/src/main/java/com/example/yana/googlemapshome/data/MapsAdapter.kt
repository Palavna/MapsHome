package com.example.yana.googlemapshome.data

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yana.googlemapshome.databinding.ItemNotesMapBinding
import java.text.SimpleDateFormat
import java.util.*

class MapsAdapter: RecyclerView.Adapter<MapsViewHolder>() {

    private val list = arrayListOf<EventLocations>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsViewHolder {
        val binding = ItemNotesMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


    fun addAll(locations: List<EventLocations>?){
        if (locations!= null){
            list.clear()
            list.addAll(locations)
            notifyDataSetChanged()
        }
    }
}

class MapsViewHolder(val binding: ItemNotesMapBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(eventLocations: EventLocations) {
//        var startTime = 0L
         eventLocations.timesInSeconds = eventLocations.endTime - eventLocations.startTime
//                val currentTime = System.currentTimeMillis()
//                val res = currentTime - startTime
                val sdf = SimpleDateFormat("H:mm:ss", Locale.getDefault())
                binding.recTimeStart.text = sdf.format()
                binding.recyclerMap.text = eventLocations.locations.toString()
                binding.recTimeStart.text = eventLocations.startTime.toString()
                binding.recTimeEnd.text = eventLocations.endTime.toString()
            }
        }

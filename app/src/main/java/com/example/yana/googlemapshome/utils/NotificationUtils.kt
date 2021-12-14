package com.example.yana.googlemapshome.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat
import com.example.yana.googlemapshome.R

object NotificationUtils {
    private const val CHANNEL_ID = "CHANNEL_ID"

     fun createNotification(context: Context): Notification {
        createNotificationChannel(context)

         val builder = androidx.core.app.NotificationCompat.Builder(context, CHANNEL_ID)
             .setSmallIcon(R.drawable.ic_android_black_24dp)
             .setContentTitle("My notification")
             .setContentText("Hello World!")
             .setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT)

         return builder.build()

    }
    fun showNotification(context: Context){
        with(NotificationManagerCompat.from(context)){
            notify(121, createNotification(context))
        }
    }


    private fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
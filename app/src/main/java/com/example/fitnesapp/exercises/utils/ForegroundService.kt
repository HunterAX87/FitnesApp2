package com.example.fitnesapp.exercises.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fitnesapp.MainActivity
import com.example.fitnesapp.R


class ForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyLog", "onCreate:$$$$ ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("intentExtra")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val notification: Notification = NotificationCompat.Builder(this, "1")
            .setContentText(input)
            .setSmallIcon(R.drawable.training)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(1, notification)
        Log.d("MyLog", "onStartCommand:$$$$ ")
        return START_NOT_STICKY
    }





    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLog", "onDestroy:$$$$ ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
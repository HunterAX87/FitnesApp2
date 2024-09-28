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

    private val CHANNEL_ID = "ForegroundServiceChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // Создание канала уведомлений
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("intentExtra")

        // Создание пустого Intent
        val notificationIntent = Intent() // Пустой Intent, который ничего не делает

        // Создание PendingIntent с пустым Intent
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Заголовок уведомления")
            .setContentText(input)
            .setSmallIcon(R.drawable.training)
            .setContentIntent(pendingIntent) // Установите созданный PendingIntent
            .setAutoCancel(true) // Уведомление исчезнет после нажатия
            .build()

        startForeground(1, notification) // Вызов метода startForeground
        Log.d("MyLog", "onStartCommand:$$$$ ")
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

//    override fun onTaskRemoved(rootIntent: Intent?) {
//        super.onTaskRemoved(rootIntent)
//        stopSelf() // Остановите сервис при удалении задачи
//        stopForeground(true)
//    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLog", "onDestroy:$$$$ ")

        // Здесь остановите звук или другие ресурсы
        // Например:
        // mediaPlayer.stop()

        stopForeground(true) // Остановите foreground service
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
package com.nicatnabiyev88.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val channelID = "com.nicatnabiyev88.notificationdemo.channel1"
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID,"DemoChannel","this is a demo")
        btn_submit.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification(){
        val notificationID = 45
        val tapResultIntent = Intent(this,SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        //action button 1
        val intent2 = Intent(this,DeteailsActivity::class.java)
        val pendingIntent2 = PendingIntent.getActivity(
            this,
            0,
            intent2,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val action2 = NotificationCompat.Action.Builder(0,"Details",pendingIntent2).build()

        //action button 2
        val intent3 = Intent(this,SettingsActivity::class.java)
        val pendingIntent3 = PendingIntent.getActivity(
            this,
            0,
            intent3,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val action3 = NotificationCompat.Action.Builder(1,"Settings",pendingIntent3).build()

        val notification = NotificationCompat.Builder(this,channelID)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(action2)
            .addAction(action3)
            .build()
        notificationManager?.notify(notificationID,notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id,name,importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
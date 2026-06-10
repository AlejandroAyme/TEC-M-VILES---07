package com.ucsm.autenticacionfirebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {

        super.onNewToken(token)

        println("TOKEN FCM:")
        println(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {

        super.onMessageReceived(message)

        createNotificationChannel()

        val builder =
            NotificationCompat.Builder(
                this,
                "canal_ucsm"
            )
                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )
                .setContentTitle(
                    message.notification?.title
                )
                .setContentText(
                    message.notification?.body
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )

        NotificationManagerCompat
            .from(this)
            .notify(
                1,
                builder.build()
            )
    }

    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(

                    "canal_ucsm",

                    "Canal Firebase",

                    NotificationManager.IMPORTANCE_HIGH
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }
    }
}
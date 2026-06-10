package com.ucsm.autenticacionfirebase

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_main
        )

        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    Log.d(
                        "FCM_TOKEN",
                        it.result
                    )
                }
            }
    }
}
package com.ucsm.seguimiento

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SegundaActividad : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val TIMER_RUNTIME = 10000
    private val TAG = "mensajeFinal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        progressBar = findViewById(R.id.progressBar)

        Thread {
            var tiempo = 0

            while (tiempo < TIMER_RUNTIME) {
                Thread.sleep(200)
                tiempo += 200

                runOnUiThread {
                    val progreso = progressBar.max * tiempo / TIMER_RUNTIME
                    progressBar.progress = progreso
                }
            }

            runOnUiThread {
                Log.d(TAG, "Su barra de progreso acaba de cargar")
            }

        }.start()
    }
}
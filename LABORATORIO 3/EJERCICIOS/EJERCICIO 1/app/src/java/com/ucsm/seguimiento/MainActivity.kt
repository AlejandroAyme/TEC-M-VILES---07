package com.ucsm.seguimiento

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ucsm.seguimiento.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "Ciclo de Vida"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "En el evento onCreate()")

        binding.btnMostrar.setOnClickListener {
            val intent = Intent(this, SegundaActividad::class.java)
            startActivity(intent)
        }
    }

    override fun onStart()   { super.onStart();   Log.d(TAG, "onStart()") }
    override fun onRestart(){ super.onRestart(); Log.d(TAG, "onRestart()") }
    override fun onResume() { super.onResume();  Log.d(TAG, "onResume()") }
    override fun onPause()  { super.onPause();   Log.d(TAG, "onPause()") }
    override fun onStop()   { super.onStop();    Log.d(TAG, "onStop()") }
    override fun onDestroy(){ super.onDestroy(); Log.d(TAG, "onDestroy()") }
}
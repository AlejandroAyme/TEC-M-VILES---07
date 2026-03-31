package com.ucsm.seguimiento

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)

        val navView = findViewById<NavigationView>(R.id.navView)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_inicio -> Toast.makeText(this,"Inicio",Toast.LENGTH_SHORT).show()
                R.id.nav_conversion -> Toast.makeText(this,"Conversión",Toast.LENGTH_SHORT).show()
            }

            true
        }
    }
}
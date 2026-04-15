package com.belen.usopreferencias

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GaleriaActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val imagenes = intArrayOf(
        R.drawable.pic01, R.drawable.pic02,
        R.drawable.pic03, R.drawable.pic04,
        R.drawable.pic05, R.drawable.pic05,
        R.drawable.pic05, R.drawable.pic05
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_galeria)

        imageView = findViewById(R.id.imageView)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = ImagenAdapter(imagenes) { position ->

            imageView.setImageResource(imagenes[position])

        }

        recyclerView.adapter = adapter

    }

}
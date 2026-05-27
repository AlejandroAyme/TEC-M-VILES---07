package com.norbel.repositoryfb

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ListaActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    private lateinit var estudiantesRef: DatabaseReference

    private lateinit var lista: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        listView = findViewById(R.id.listView)

        lista = mutableListOf()

        estudiantesRef = FirebaseDatabase.getInstance()
            .getReference("Estudiantes")

        leerDatos()
    }

    private fun leerDatos() {

        estudiantesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                lista.clear()

                for (dato in snapshot.children) {

                    val estudiante =
                        dato.getValue(Estudiante::class.java)

                    estudiante?.let {

                        lista.add(
                            "Nombre: ${it.nombre}\n" +
                                    "Carrera: ${it.carrera}\n" +
                                    "Curso: ${it.curso}"
                        )
                    }
                }

                val adapter = ArrayAdapter(
                    this@ListaActivity,
                    android.R.layout.simple_list_item_1,
                    lista
                )

                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
package com.example.estudiantesfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ListaEstudiantesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var lista: ArrayList<Estudiante>
    private lateinit var adapter: EstudianteAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_estudiantes)

        recyclerView = findViewById(R.id.recyclerEstudiantes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        lista = ArrayList()

        adapter = EstudianteAdapter(lista)

        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("Estudiantes")

        leerDatos()
    }

    private fun leerDatos() {

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                lista.clear()

                for (dato in snapshot.children) {

                    val estudiante = dato.getValue(Estudiante::class.java)

                    if (estudiante != null) {
                        lista.add(estudiante)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
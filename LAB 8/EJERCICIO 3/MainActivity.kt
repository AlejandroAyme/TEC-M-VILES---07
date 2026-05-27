package com.norbel.repositoryfb

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtCarrera: EditText
    private lateinit var txtCurso: EditText

    private lateinit var btnGuardar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnVer: Button

    private lateinit var estudiantesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtNombre)
        txtCarrera = findViewById(R.id.txtCarrera)
        txtCurso = findViewById(R.id.txtCurso)

        btnGuardar = findViewById(R.id.btnGuardar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnVer = findViewById(R.id.btnVer)

        estudiantesRef = FirebaseDatabase.getInstance()
            .getReference("Estudiantes")

        btnGuardar.setOnClickListener {
            guardarEstudiante()
        }

        btnActualizar.setOnClickListener {
            actualizarEstudiante()
        }

        btnEliminar.setOnClickListener {
            eliminarEstudiante()
        }

        btnVer.setOnClickListener {
            startActivity(Intent(this, ListaActivity::class.java))
        }
    }

    private fun guardarEstudiante() {

        val nombre = txtNombre.text.toString()
        val carrera = txtCarrera.text.toString()
        val curso = txtCurso.text.toString()

        if (nombre.isEmpty() || carrera.isEmpty() || curso.isEmpty()) {

            Toast.makeText(
                this,
                "Complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val id = estudiantesRef.push().key!!

        val estudiante = Estudiante(
            id,
            nombre,
            carrera,
            curso
        )

        estudiantesRef.child(id)
            .setValue(estudiante)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Estudiante guardado",
                    Toast.LENGTH_SHORT
                ).show()

                limpiarCampos()
            }
    }

    private fun actualizarEstudiante() {

        val nombre = txtNombre.text.toString()

        if (nombre.isEmpty()) {

            Toast.makeText(
                this,
                "Ingrese el nombre a actualizar",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        estudiantesRef.orderByChild("nombre")
            .equalTo(nombre)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        for (dato in snapshot.children) {

                            val mapa = HashMap<String, Any>()

                            mapa["curso"] = "Curso Actualizado"

                            dato.ref.updateChildren(mapa)
                        }

                        Toast.makeText(
                            this@MainActivity,
                            "Registro actualizado",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this@MainActivity,
                            "No existe el estudiante",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun eliminarEstudiante() {

        val nombre = txtNombre.text.toString()

        if (nombre.isEmpty()) {

            Toast.makeText(
                this,
                "Ingrese nombre a eliminar",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        estudiantesRef.orderByChild("nombre")
            .equalTo(nombre)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {

                        for (dato in snapshot.children) {

                            dato.ref.removeValue()
                        }

                        Toast.makeText(
                            this@MainActivity,
                            "Registro eliminado",
                            Toast.LENGTH_SHORT
                        ).show()

                        limpiarCampos()

                    } else {

                        Toast.makeText(
                            this@MainActivity,
                            "No encontrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun limpiarCampos() {

        txtNombre.setText("")
        txtCarrera.setText("")
        txtCurso.setText("")
    }
}
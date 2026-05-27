package com.norbel.registroestudiantesfb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtCarrera: EditText
    private lateinit var txtCurso: EditText
    private lateinit var btnGuardar: Button

    private lateinit var estudiantesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtNombre)
        txtCarrera = findViewById(R.id.txtCarrera)
        txtCurso = findViewById(R.id.txtCurso)
        btnGuardar = findViewById(R.id.btnGuardar)

        estudiantesRef =
            FirebaseDatabase.getInstance().getReference("Estudiantes")

        btnGuardar.setOnClickListener {

            guardarEstudiante()

        }
    }

    private fun guardarEstudiante() {

        val nombre = txtNombre.text.toString().trim()
        val carrera = txtCarrera.text.toString().trim()
        val curso = txtCurso.text.toString().trim()

        if (nombre.isEmpty()) {

            Toast.makeText(
                this,
                "Ingrese nombre",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (carrera.isEmpty()) {

            Toast.makeText(
                this,
                "Ingrese carrera",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (curso.isEmpty()) {

            Toast.makeText(
                this,
                "Ingrese curso",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val id =
            estudiantesRef.push().key ?: return

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
                    Toast.LENGTH_LONG
                ).show()

                txtNombre.text.clear()
                txtCarrera.text.clear()
                txtCurso.text.clear()
            }

            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Error al guardar",
                    Toast.LENGTH_LONG
                ).show()

            }
    }
}
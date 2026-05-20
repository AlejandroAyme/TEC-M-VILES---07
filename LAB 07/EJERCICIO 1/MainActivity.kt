package com.norbel.archivoexterno

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.norbel.archivoexterno.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {

        private const val FILE_NAME = "archivo.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {

            guardarArchivo()
        }

        binding.btnCargar.setOnClickListener {

            cargarArchivo()
        }
    }

    private fun guardarArchivo() {

        val texto = binding.editText.text.toString()

        try {

            val archivo = File(
                getExternalFilesDir(null),
                FILE_NAME
            )

            archivo.writeText(texto)

            Toast.makeText(
                this,
                "Archivo guardado correctamente",
                Toast.LENGTH_SHORT
            ).show()

            binding.editText.setText("")

        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(
                this,
                "Error al guardar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun cargarArchivo() {

        try {

            val archivo = File(
                getExternalFilesDir(null),
                FILE_NAME
            )

            val contenido = archivo.readText()

            binding.editText.setText(contenido)

            Toast.makeText(
                this,
                "Archivo cargado correctamente",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: java.io.FileNotFoundException) {

            Toast.makeText(
                this,
                "El archivo no existe aún",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(
                this,
                "Error al cargar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
package com.norbel.gimnasio

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.norbel.gimnasio.data.AppDatabase
import com.norbel.gimnasio.data.Articulo
import com.norbel.gimnasio.data.ArticuloDao
import com.norbel.gimnasio.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dao: ArticuloDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dao = AppDatabase
            .getInstance(this)
            .articuloDao()

        binding.btnRegistrar.setOnClickListener {

            registrar()
        }

        binding.btnBuscar.setOnClickListener {

            buscar()
        }

        binding.btnModificar.setOnClickListener {

            modificar()
        }

        binding.btnEliminar.setOnClickListener {

            eliminar()
        }

        observarDatos()
    }

    private fun toast(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun limpiarCampos() {

        binding.txtCodigo.setText("")
        binding.txtNombre.setText("")
        binding.txtMembresia.setText("")
    }

    private fun registrar() {

        val codigo =
            binding.txtCodigo.text.toString()

        val nombre =
            binding.txtNombre.text.toString()

        val membresia =
            binding.txtMembresia.text.toString()

        if (
            codigo.isEmpty() ||
            nombre.isEmpty() ||
            membresia.isEmpty()
        ) {

            toast("Complete todos los campos")

            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            nombre,
            membresia.toDouble()
        )

        lifecycleScope.launch {

            try {

                dao.insertar(articulo)

                toast("Registro exitoso")

                limpiarCampos()

            } catch (e: Exception) {

                toast("Error: ${e.message}")
            }
        }
    }

    private fun buscar() {

        val codigo =
            binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {

            toast("Ingrese el código")

            return
        }

        lifecycleScope.launch {

            val articulo =
                dao.buscarPorCodigo(
                    codigo.toInt()
                )

            if (articulo != null) {

                binding.txtNombre
                    .setText(articulo.descripcion)

                binding.txtMembresia
                    .setText(articulo.precio.toString())

            } else {

                toast("No existe")
            }
        }
    }

    private fun modificar() {

        val codigo =
            binding.txtCodigo.text.toString()

        val nombre =
            binding.txtNombre.text.toString()

        val membresia =
            binding.txtMembresia.text.toString()

        if (
            codigo.isEmpty() ||
            nombre.isEmpty() ||
            membresia.isEmpty()
        ) {

            toast("Complete todos los campos")

            return
        }

        val articulo = Articulo(
            codigo.toInt(),
            nombre,
            membresia.toDouble()
        )

        lifecycleScope.launch {

            val filas =
                dao.actualizar(articulo)

            if (filas == 1) {

                toast("Modificado")

            } else {

                toast("No existe")
            }
        }
    }

    private fun eliminar() {

        val codigo =
            binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {

            toast("Ingrese código")

            return
        }

        lifecycleScope.launch {

            val filas =
                dao.eliminarPorCodigo(
                    codigo.toInt()
                )

            if (filas == 1) {

                toast("Eliminado")

                limpiarCampos()

            } else {

                toast("No existe")
            }
        }
    }

    private fun observarDatos() {

        lifecycleScope.launch {

            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                dao.listarTodos().collect { lista ->

                    var texto = ""

                    for (articulo in lista) {

                        texto +=
                            "Código: ${articulo.codigo}\n" +
                            "Nombre: ${articulo.descripcion}\n" +
                            "Membresía: ${articulo.precio}\n\n"
                    }

                    binding.txtLista.text = texto
                }
            }
        }
    }
}
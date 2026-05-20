package com.norbel.gimnasiocrud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.norbel.gimnasiocrud.data.AppDatabase
import com.norbel.gimnasiocrud.data.Cliente
import com.norbel.gimnasiocrud.data.ClienteDao
import com.norbel.gimnasiocrud.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dao: ClienteDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dao = AppDatabase
            .getDatabase(this)
            .clienteDao()

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
    }

    private fun toast(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun limpiar() {

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
            codigo.isEmpty()
            || nombre.isEmpty()
            || membresia.isEmpty()
        ) {

            toast("Complete todos los campos")

            return
        }

        val cliente = Cliente(
            codigo.toInt(),
            nombre,
            membresia
        )

        lifecycleScope.launch {

            dao.insertar(cliente)

            limpiar()

            toast("Cliente registrado")
        }
    }

    private fun buscar() {

        val codigo =
            binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {

            toast("Ingrese código")

            return
        }

        lifecycleScope.launch {

            val cliente =
                dao.buscar(codigo.toInt())

            if (cliente != null) {

                binding.txtNombre
                    .setText(cliente.nombre)

                binding.txtMembresia
                    .setText(cliente.membresia)

            } else {

                toast("Cliente no existe")
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

        val cliente = Cliente(
            codigo.toInt(),
            nombre,
            membresia
        )

        lifecycleScope.launch {

            dao.actualizar(cliente)

            toast("Cliente modificado")
        }
    }

    private fun eliminar() {

        val codigo =
            binding.txtCodigo.text.toString()

        lifecycleScope.launch {

            dao.eliminarPorCodigo(
                codigo.toInt()
            )

            limpiar()

            toast("Cliente eliminado")
        }
    }
}
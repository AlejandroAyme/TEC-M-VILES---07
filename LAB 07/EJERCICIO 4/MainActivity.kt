package com.norbel.gimnasioroom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.norbel.gimnasioroom.data.AppDatabase
import com.norbel.gimnasioroom.data.Cliente
import com.norbel.gimnasioroom.databinding.ActivityMainBinding
import com.norbel.gimnasioroom.repository.ClienteRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: ClienteRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val dao =
            AppDatabase
                .getDatabase(this)
                .clienteDao()

        repository = ClienteRepository(dao)

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

        observarClientes()
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

        val cliente = Cliente(
            codigo.toInt(),
            nombre,
            membresia
        )

        lifecycleScope.launch {

            repository.insertar(cliente)

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
                repository.buscar(codigo.toInt())

            if (cliente != null) {

                binding.txtNombre.setText(cliente.nombre)

                binding.txtMembresia.setText(cliente.membresia)

            } else {

                toast("Cliente no encontrado")
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

            repository.actualizar(cliente)

            toast("Cliente modificado")
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

            repository.eliminarPorCodigo(
                codigo.toInt()
            )

            toast("Cliente eliminado")
        }
    }

    private fun observarClientes() {

        lifecycleScope.launch {

            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                repository
                    .listarTodos()
                    .collect { lista ->

                        var texto = ""

                        for (cliente in lista) {

                            texto +=
                                "Código: ${cliente.codigo}\n" +
                                "Nombre: ${cliente.nombre}\n" +
                                "Membresía: ${cliente.membresia}\n\n"
                        }

                        binding.txtLista.text = texto
                    }
            }
        }
    }

    private fun toast(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }
}
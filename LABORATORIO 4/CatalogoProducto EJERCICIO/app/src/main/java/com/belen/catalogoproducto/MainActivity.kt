package com.belen.catalogoproducto

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductoAdapter

    private lateinit var edtNombre: EditText
    private lateinit var edtCantidad: EditText
    private lateinit var edtPrecio: EditText

    private val gson = Gson()
    private var listaProductos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("productos_xml", MODE_PRIVATE)

        edtNombre = findViewById(R.id.edtNombre)
        edtCantidad = findViewById(R.id.edtCantidad)
        edtPrecio = findViewById(R.id.edtPrecio)

        recycler = findViewById(R.id.recyclerProductos)
        recycler.layoutManager = LinearLayoutManager(this)

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnHistorial = findViewById<Button>(R.id.btnHistorial)

        cargarLista()

        adapter = ProductoAdapter(listaProductos) { position ->
            eliminarProducto(position)
        }

        recycler.adapter = adapter

        btnAgregar.setOnClickListener {
            agregarProducto()
        }

        btnHistorial.setOnClickListener {
            cargarLista()
            actualizarRecycler()
        }
    }

    private fun agregarProducto() {
        val nombre = edtNombre.text.toString()
        val cantidad = edtCantidad.text.toString().toIntOrNull() ?: 0
        val precio = edtPrecio.text.toString().toDoubleOrNull() ?: 0.0

        val nuevo = Producto(nombre, cantidad, precio)

        listaProductos.add(nuevo)
        guardarLista()
        actualizarRecycler()

        edtNombre.text.clear()
        edtCantidad.text.clear()
        edtPrecio.text.clear()
    }

    private fun eliminarProducto(position: Int) {
        listaProductos.removeAt(position)
        guardarLista()
        actualizarRecycler()
    }

    private fun guardarLista() {
        val json = gson.toJson(listaProductos)
        prefs.edit().putString("lista_productos", json).apply()
    }

    private fun cargarLista() {
        val json = prefs.getString("lista_productos", null)

        listaProductos.clear()

        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Producto>>() {}.type
            val data: MutableList<Producto> = gson.fromJson(json, type)
            listaProductos.addAll(data)
        }
    }

    private fun actualizarRecycler() {
        adapter.notifyDataSetChanged()
    }
}
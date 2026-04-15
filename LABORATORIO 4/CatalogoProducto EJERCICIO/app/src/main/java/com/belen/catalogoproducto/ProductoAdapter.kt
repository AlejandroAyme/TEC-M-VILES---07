package com.belen.catalogoproducto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val lista: MutableList<Producto>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val cantidad = view.findViewById<TextView>(R.id.txtCantidad)
        val precio = view.findViewById<TextView>(R.id.txtPrecio)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.nombre.text = item.nombre
        holder.cantidad.text = "Cantidad: ${item.cantidad}"
        holder.precio.text = "S/. ${item.precio}"

        holder.btnEliminar.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = lista.size
}
package com.example.estudiantesfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EstudianteAdapter(
    private val lista: ArrayList<Estudiante>
) : RecyclerView.Adapter<EstudianteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val carrera: TextView = itemView.findViewById(R.id.txtCarrera)
        val curso: TextView = itemView.findViewById(R.id.txtCurso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)

        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val estudiante = lista[position]

        holder.nombre.text = "Nombre: ${estudiante.nombre}"
        holder.carrera.text = "Carrera: ${estudiante.carrera}"
        holder.curso.text = "Curso: ${estudiante.curso}"
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
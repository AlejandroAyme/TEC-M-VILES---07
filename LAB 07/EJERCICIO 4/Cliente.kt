package com.norbel.gimnasiocrud.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")

data class Cliente(

    @PrimaryKey
    val codigo: Int,

    val nombre: String,

    val membresia: String
)
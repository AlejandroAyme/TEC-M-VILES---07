package com.norbel.gimnasiocrud.data

import androidx.room.*

@Dao
interface ClienteDao {

    @Insert
    suspend fun insertar(cliente: Cliente)

    @Update
    suspend fun actualizar(cliente: Cliente)

    @Delete
    suspend fun eliminar(cliente: Cliente)

    @Query("SELECT * FROM clientes WHERE codigo = :codigo LIMIT 1")
    suspend fun buscar(codigo: Int): Cliente?

    @Query("DELETE FROM clientes WHERE codigo = :codigo")
    suspend fun eliminarPorCodigo(codigo: Int): Int
}
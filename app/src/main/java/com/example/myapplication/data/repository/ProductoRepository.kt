package com.example.myapplication.data.repository

import com.example.myapplication.data.model.ProductoDto
import com.example.myapplication.data.remote.ProductoApi

class ProductoRepository(private val api: ProductoApi) {
    suspend fun listar(): List<ProductoDto> = api.listar()

    suspend fun obtener(id: Long): ProductoDto = api.obtener(id)

    suspend fun crear(nombre: String, descripcion: String?, imagenUrl: String?, precio: Int, visibilidad: Boolean): ProductoDto =
        api.crear(ProductoDto(nombre = nombre, descripcion = descripcion, imagenUrl = imagenUrl, precio = precio, visibilidad = visibilidad))

    suspend fun actualizar(id: Long, nombre: String, descripcion: String?, imagenUrl: String?, precio: Int, visibilidad: Boolean): ProductoDto =
        api.actualizar(id, ProductoDto(id = id, nombre = nombre, descripcion = descripcion, imagenUrl = imagenUrl, precio = precio, visibilidad = visibilidad))

    suspend fun eliminar(id: Long) = api.eliminar(id)
}

package com.example.myapplication.data.remote

import com.example.myapplication.data.model.ProductoDto
import retrofit2.http.*

interface ProductoApi {
    @GET("api/productos")
    suspend fun listar(): List<ProductoDto>

    @GET("api/productos/{id}")
    suspend fun obtener(@Path("id") id: Long): ProductoDto

    @POST("api/productos")
    suspend fun crear(@Body producto: ProductoDto): ProductoDto

    @PUT("api/productos/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body producto: ProductoDto): ProductoDto

    @DELETE("api/productos/{id}")
    suspend fun eliminar(@Path("id") id: Long)
}

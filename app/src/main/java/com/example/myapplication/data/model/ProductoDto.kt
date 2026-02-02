package com.example.myapplication.data.model

import java.math.BigDecimal

data class ProductoDto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String? = null,
    val imagenUrl: String? = null,
    val precio: Int,
    val visibilidad: Boolean = true
)

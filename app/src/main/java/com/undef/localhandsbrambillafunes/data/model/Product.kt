package com.undef.localhandsbrambillafunes.data.model

// Modelo de datos para un producto
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val producer: String,
    val category: String,
    val imageId: Int = 0
)
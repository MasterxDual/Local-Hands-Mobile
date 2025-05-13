// Product.kt
package com.undef.localhandsbrambillafunes.data.model

// Modelo de datos para un producto
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val producer: String,
    val category: String,
    val images: List<String>, // Mínimo 1 imagen, máximo 10
    val price: Double,
    val location: String
)
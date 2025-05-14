package com.undef.localhandsbrambillafunes.data.model

// Modelo de datos para un producto
data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val images: List<String>, // Mínimo 1 imagen, máximo 10
)


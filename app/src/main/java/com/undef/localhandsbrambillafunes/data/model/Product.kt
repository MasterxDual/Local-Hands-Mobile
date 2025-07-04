// Product.kt
package com.undef.localhandsbrambillafunes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Modelo de datos para un producto
@Entity(tableName = "ProductEntity")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val producer: String,
    val category: String,
    val images: List<String>, // Mínimo 1 imagen, máximo 10
    val price: Double,
    val location: String
)
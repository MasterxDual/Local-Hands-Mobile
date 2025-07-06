package com.undef.localhandsbrambillafunes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Modelo de datos para un producto
@Entity(tableName = "CategoryEntity")
data class Category(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val images: List<String>, // Mínimo 1 imagen, máximo 10
)


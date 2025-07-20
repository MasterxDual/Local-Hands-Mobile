package com.undef.localhandsbrambillafunes.data.local.model

import com.undef.localhandsbrambillafunes.data.local.entities.Product

// Para esta primera etapa guardamos los favoritos dentro de un archivo de configuracion para datos simples
object FavoriteProducts {
    // Lista en memoria (se borra al cerrar la app)
    private val favoriteProducts = mutableListOf<Product>()

    // Añadir un producto a favoritos (si no existe ya)
    fun addToFavorite(product: Product) {
        if (!favoriteProducts.any { it.id == product.id }) {
            favoriteProducts.add(product)
        }
    }

    // Eliminar un producto de favoritos por su ID
    fun removeToFavorite(productId: Int) {
        favoriteProducts.removeIf { it.id == productId }
    }

    // Obtener la lista completa de favoritos
    fun getFavorites(): List<Product> {
        return favoriteProducts.toList() // Copia inmutable
    }

    // Obtener si un producto esta en favoritos
    fun isFavorite(productId: Int): Boolean {
        return favoriteProducts.any { it.id == productId }
    }
}
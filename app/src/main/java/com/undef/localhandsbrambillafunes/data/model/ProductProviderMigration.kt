package com.undef.localhandsbrambillafunes.data.model

import kotlin.collections.joinToString

object ProductProviderMigration {
    fun getAllAsEntities(): List<Product> = ProductProvider.products.map { prod ->
        Product(
            id = prod.id,
            name = prod.name,
            description = prod.description,
            producer = prod.producer,
            category = prod.category,
            images = prod.images, // Simple serialización
            price = prod.price,
            location = prod.location
        )
    }
}

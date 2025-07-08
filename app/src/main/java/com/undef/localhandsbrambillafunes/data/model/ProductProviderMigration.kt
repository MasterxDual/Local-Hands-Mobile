package com.undef.localhandsbrambillafunes.data.model

import com.undef.localhandsbrambillafunes.data.model.entities.Product


/**
 * Objeto encargado de realizar la migración de productos desde una fuente externa ([ProductProvider])
 * hacia entidades del modelo local [com.undef.localhandsbrambillafunes.data.model.entities.Product] utilizadas por la base de datos (Room).
 *
 * Este proceso se suele utilizar en etapas de inicialización de datos o sincronización local.
 *
 * ## Funcionalidad principal:
 * - Transforma una lista de productos externos (`ProductProvider.products`) en una lista de
 *   entidades `Product` compatibles con la base de datos.
 * - Cada propiedad se asigna directamente, incluyendo las imágenes, que se asumen como ya serializadas
 *   o adecuadas para almacenamiento.
 *
 * ### Nota:
 * - No realiza validaciones sobre la cantidad o formato de imágenes.
 * - Se espera que el proveedor externo tenga una estructura de datos compatible con el modelo `Product`.
 */
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
            location = prod.location,
            ownerId = prod.ownerId
        )
    }
}

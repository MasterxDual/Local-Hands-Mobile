package com.undef.localhandsbrambillafunes.data.model

/**
 * Representa una categoría dentro del sistema de productos de la aplicación.
 *
 * Cada categoría agrupa un conjunto de productos relacionados bajo un nombre común y una descripción,
 * y puede estar asociada visualmente a una o varias imágenes representativas.
 *
 * ## Propiedades:
 *
 * @property id Identificador único de la categoría. Usado para distinguirla dentro de la base de datos o lógica de negocio.
 * @property name Nombre de la categoría. Debe ser breve y representativo (por ejemplo: "Artesanías", "Textiles").
 * @property description Descripción ampliada de la categoría. Puede incluir detalles sobre los productos que agrupa o su finalidad.
 * @property images Lista de rutas (o URIs) de imágenes asociadas a la categoría.
 *                 - **Debe contener al menos una imagen.**
 *                 - **Máximo permitido: 10 imágenes.**
 *                 - Estas imágenes se utilizan para representar visualmente la categoría en la interfaz de usuario.
 *
 * ## Ejemplo de uso:
 * ```kotlin
 * val category = Category(
 *     id = 1,
 *     name = "Cosmética Natural",
 *     description = "Productos hechos a base de ingredientes naturales, sin químicos.",
 *     images = listOf("path/to/image1.jpg", "path/to/image2.jpg")
 * )
 * ```
 *
 * ## Consideraciones:
 * - Las validaciones de cantidad mínima y máxima de imágenes deben ser aplicadas en la lógica de negocio o interfaz.
 * - Esta clase puede integrarse con bases de datos locales como Room o utilizarse directamente en vistas.
 */
data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val images: List<String>, // Mínimo 1 imagen, máximo 10
)
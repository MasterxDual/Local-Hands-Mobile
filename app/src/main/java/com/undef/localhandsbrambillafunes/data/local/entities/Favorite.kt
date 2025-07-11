package com.undef.localhandsbrambillafunes.data.local.entities

import androidx.room.Entity

/**
 * Entidad que representa una relación de "favorito" entre un usuario y un producto dentro de la base de datos local.
 *
 * Esta entidad modela una relación **muchos a muchos** entre usuarios y productos, donde un usuario puede
 * marcar múltiples productos como favoritos y un producto puede ser favorito de múltiples usuarios.
 *
 * ## Configuración de Room:
 * - Se utiliza la anotación `@Entity` con `tableName = "FavoriteEntity"` para especificar el nombre de la tabla.
 * - Los campos `userId` y `productId` forman una **clave primaria compuesta**, lo que garantiza que un producto
 *   no pueda ser marcado como favorito más de una vez por el mismo usuario.
 *
 * ## Propiedades:
 *
 * @property userId ID del usuario que ha marcado el producto como favorito.
 * @property productId ID del producto que fue marcado como favorito.
 *
 * ## Ejemplo de uso:
 * ```kotlin
 * val favorite = Favorite(userId = 5, productId = 12)
 * favoriteDao.insert(favorite)
 * ```
 *
 * ## Consideraciones:
 * - Se espera que tanto `userId` como `productId` correspondan a claves existentes en las tablas `User` y `Product`, respectivamente.
 * - Ideal para implementar funcionalidades como "productos favoritos del usuario" o "wishlist".
 * - Puede ser accedida a través de consultas que unan con las tablas `User` y `Product` para recuperar información relacionada.
 */
@Entity(tableName = "FavoriteEntity", primaryKeys = ["userId", "productId"])
data class Favorite(
    val userId: Int,
    val productId: Int
)

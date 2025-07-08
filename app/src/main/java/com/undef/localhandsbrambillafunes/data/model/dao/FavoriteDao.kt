package com.undef.localhandsbrambillafunes.data.model.dao

import androidx.room.*
import com.undef.localhandsbrambillafunes.data.model.entities.Favorite
import com.undef.localhandsbrambillafunes.data.model.entities.Product
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz DAO (Data Access Object) para gestionar la relación de favoritos entre usuarios y productos.
 *
 * Esta interfaz proporciona métodos para insertar, eliminar y consultar productos marcados como favoritos
 * por los usuarios. Está diseñada para ser utilizada con la librería Room de Android.
 *
 * ## Operaciones disponibles:
 * - Agregar un producto a la lista de favoritos de un usuario.
 * - Eliminar un producto de la lista de favoritos.
 * - Consultar todos los productos favoritos de un usuario específico.
 */
@Dao
interface FavoriteDao {

    /**
     * Inserta un nuevo registro de favorito en la base de datos.
     *
     * Si ya existe un favorito con la misma combinación de `userId` y `productId`,
     * se reemplazará gracias a la política `OnConflictStrategy.REPLACE`.
     *
     * @param favorite Objeto que representa la relación entre el usuario y el producto favorito.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    /**
     * Elimina un producto de la lista de favoritos de un usuario.
     *
     * @param favorite Objeto que representa la relación a eliminar.
     */
    @Delete
    suspend fun removeFavorite(favorite: Favorite)

    /**
     * Recupera todos los productos que han sido marcados como favoritos por un usuario determinado.
     *
     * Esta consulta realiza un `INNER JOIN` entre la tabla de productos (`ProductEntity`)
     * y la tabla de favoritos (`FavoriteEntity`) para retornar únicamente los productos asociados
     * al `userId` especificado.
     *
     * @param userId ID del usuario del cual se desean obtener los productos favoritos.
     * @return Un `Flow` reactivo que emite la lista de productos favoritos cada vez que cambia.
     */
    @Query("""
        SELECT p.* FROM ProductEntity p 
        INNER JOIN FavoriteEntity f ON p.id = f.productId 
        WHERE f.userId = :userId
    """)
    fun getFavoritesForUser(userId: Int): Flow<List<Product>>
}
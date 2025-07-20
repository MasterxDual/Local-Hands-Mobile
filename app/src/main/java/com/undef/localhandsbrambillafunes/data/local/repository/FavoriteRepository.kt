package com.undef.localhandsbrambillafunes.data.local.repository

import com.undef.localhandsbrambillafunes.data.local.dao.FavoriteDao
import com.undef.localhandsbrambillafunes.data.local.entities.Favorite

/**
 * Repositorio encargado de manejar las operaciones relacionadas con la entidad `Favorite`.
 *
 * Esta clase actúa como intermediario entre el DAO (`FavoriteDao`) y las capas superiores de la aplicación
 * (como ViewModel o UseCase), encapsulando el acceso a los datos y permitiendo una mejor separación de responsabilidades.
 *
 * @param favoriteDao Instancia de `FavoriteDao` utilizada para ejecutar las operaciones sobre la base de datos.
 */
class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    /**
     * Agrega un nuevo favorito a la base de datos.
     *
     * @param favorite Objeto `Favorite` que se desea insertar.
     */
    suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.addFavorite(favorite)
    }

    /**
     * Elimina un favorito existente de la base de datos.
     *
     * @param userId Identificador único del usuario.
     * @param productId Identificador único del producto.
     */
    suspend fun removeFavorite(userId: Int, productId: Int) {
        favoriteDao.removeFavoriteByUserAndProduct(userId, productId)
    }

    /**
     * Obtiene la lista de elementos marcados como favoritos por un usuario específico.
     *
     * @param userId Identificador único del usuario.
     * @return Un `Flow` que emite la lista de objetos `Favorite` del usuario.
     */
    fun getFavoritesForUser(userId: Int) = favoriteDao.getFavoritesForUser(userId)

}
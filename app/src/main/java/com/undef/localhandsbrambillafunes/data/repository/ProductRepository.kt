package com.undef.localhandsbrambillafunes.data.repository

import com.undef.localhandsbrambillafunes.data.model.entities.Product
import com.undef.localhandsbrambillafunes.data.model.db.ProductDatabase
import com.undef.localhandsbrambillafunes.data.model.entities.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * El flujo recomendado es el siguiente: **DAO → Repository → ViewModel → UI (Compose)**.
 *
 * Utilizamos la arquitectura MVVM: Model-View-ViewModel.
 *
 * Encargado de conectar el DAO con el ViewModel.
 *
 * **Responsabilidad principal**: Abstraer el acceso a múltiples fuentes de datos (local, remoto,
 * cache, etc.) y proporcionar una API limpia al resto de la aplicación.
 *
 * **¿Para qué sirve?**
 *
 * --> Intermediario entre la base de datos (Room) y el ViewModel.
 *
 * --> Encapsula la lógica de acceso a datos y la forma en que se obtienen (por ejemplo, Room, Retrofit, DataStore, etc.).
 *
 * --> Facilita pruebas unitarias porque se puede simular fácilmente.
 *
 * --> Mejora la escalabilidad y mantenibilidad del código.
 * @param com.undef.localhandsbrambillafunes.data.model.db.ProductDatabase base de datos de productos.
 * */
class ProductRepository(private val db: ProductDatabase) {

    /**
     * Obtiene todos los productos como un flujo reactivo.
     *
     * @return Un [kotlinx.coroutines.flow.Flow] que emite listas de productos almacenados en la base de datos.
     */
    fun getAllProducts(): Flow<List<Product>> {
        return db.productDao().getAllProducts()
    }

    /**
     * Obtiene un flujo reactivo con todos los productos publicados por un usuario específico.
     *
     * Este método consulta la base de datos local y retorna los productos cuyo `ownerId`
     * coincida con el ID proporcionado. El resultado se entrega como un `Flow`, lo cual permite
     * que la UI se actualice automáticamente si los datos cambian.
     *
     * @param ownerId Identificador único del usuario (vendedor) cuyos productos se desean obtener.
     * @return Un flujo (`Flow`) que emite listas de productos asociados al vendedor.
     */
    fun getProductsByOwner(ownerId: Int): Flow<List<Product>> =
        db.productDao().getProductsByOwner(ownerId)

    /**
     * Obtiene una lista de productos filtrados por una categoría específica.
     *
     * @param category Categoría por la cual se desea filtrar los productos.
     * @return Lista de productos que pertenecen a la categoría proporcionada.
     */
    suspend fun getProductsByCategory(category: String): List<Product> =
        withContext(Dispatchers.IO) {
            db.productDao().getProductsByCategory(category)
        }

    /**
     * Inserta un nuevo producto en la base de datos.
     *
     * @param product Instancia del producto a insertar.
     * @return El ID generado para el nuevo producto.
     */
    suspend fun insertProduct(product: Product): Long = withContext(Dispatchers.IO) {
        db.productDao().addProduct(product)
    }

    /**
     * Obtiene productos según la ciudad indicada.
     *
     * @param location Ciudad donde se encuentran los productos.
     * @return Lista de productos localizados en la ciudad especificada.
     */
    suspend fun getProductsByCity(location: String): List<Product> = withContext(Dispatchers.IO) {
        db.productDao().getProductsByCity(location)
    }

    /**
     * Busca productos cuyo nombre de vendedor coincida parcial o totalmente con el nombre indicado.
     *
     * @param name Nombre o parte del nombre del vendedor.
     * @return Lista de productos asociados a vendedores con ese nombre.
     */
    suspend fun searchProductsBySeller(name: String): List<Product> = withContext(Dispatchers.IO) {
        db.productDao().searchProductsBySeller(name)
    }

    /**
     * Actualiza los datos de un producto existente en la base de datos.
     *
     * @param product Producto con la información actualizada.
     */
    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().updateProduct(product)
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param product Producto que se desea eliminar.
     */
    suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().deleteProduct(product)
    }

    /**
     * Inserta una lista de productos en la base de datos, reemplazando los existentes si hay conflicto.
     * Ideal para sincronización masiva desde un servidor remoto.
     *
     * @param products Lista de productos a insertar o actualizar.
     */
    suspend fun insertAll(products: List<Product>) = withContext(Dispatchers.IO) {
        db.productDao().insertAll(products)
    }

    // --- FAVORITOS ---

    /**
     * Obtiene un flujo reactivo con todos los productos marcados como favoritos por un usuario.
     *
     * Este método realiza una consulta `JOIN` entre las tablas de productos y favoritos,
     * devolviendo los productos que han sido agregados como favoritos por el usuario identificado.
     *
     * @param userId ID del usuario del cual se desean obtener los productos favoritos.
     * @return Un `Flow` que emite listas de productos favoritos en tiempo real.
     */
    fun getFavoritesForUser(userId: Int): Flow<List<Product>> =
        db.favoriteDao().getFavoritesForUser(userId)

    /**
     * Agrega un producto a la lista de favoritos de un usuario.
     *
     * Esta operación inserta una nueva entrada en la tabla de favoritos (`FavoriteEntity`)
     * asociando el `userId` con el `productId` correspondiente.
     * Si ya existía una relación igual, será reemplazada.
     *
     * @param userId ID del usuario que marca el producto como favorito.
     * @param productId ID del producto que se desea agregar a la lista de favoritos.
     */
    suspend fun addFavorite(userId: Int, productId: Int) = withContext(Dispatchers.IO) {
        db.favoriteDao().addFavorite(Favorite(userId, productId))
    }

    /**
     * Elimina un producto de la lista de favoritos de un usuario.
     *
     * Esta operación elimina la entrada correspondiente en la tabla de favoritos (`FavoriteEntity`)
     * que vincula al usuario con el producto indicado.
     *
     * @param userId ID del usuario que desea eliminar el producto de sus favoritos.
     * @param productId ID del producto a eliminar de la lista de favoritos.
     */
    suspend fun removeFavorite(userId: Int, productId: Int) = withContext(Dispatchers.IO) {
        db.favoriteDao().removeFavorite(Favorite(userId, productId))
    }
}
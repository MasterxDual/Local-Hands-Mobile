package com.undef.localhandsbrambillafunes.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.undef.localhandsbrambillafunes.data.model.Product
import kotlinx.coroutines.flow.Flow

/**
* Data Access Object (DAO) para acceder y manipular los datos de productos en la base de datos local.
* Proporciona métodos para realizar operaciones CRUD sobre la entidad [Product], incluyendo
* filtrado por categoría, ciudad, nombre del vendedor, y funcionalidades para manejar favoritos.
*/
@Dao
interface ProductDao {

    /**
    * Consulta SQL para obtener todos los productos de la tabla de la base de datos
    * Obtiene todos los productos almacenados en la base de datos como un flujo reactivo.
    * @return [Flow] que emite una lista de todos los productos.
    */
    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): Flow<List<Product>>


    /**
     * Consulta SQL para insertar un producto en la tabla de la base de datos
     * @param product Producto a insertar.
     * @return ID generado del producto insertado.
     */
    @Insert
    suspend fun addProduct(product: Product): Long

    /**
     * Consulta SQL para obtener una lista de productos que pertenecen a una categoría específica
     * Requerimiento: “Filtrar por categoría de productos”
     * @param category Categoría de productos a filtrar.
     * @return Lista de productos que pertenecen a la categoría dada.
     */
    @Query("SELECT * FROM ProductEntity WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<Product>

    /**
     * Obtiene productos según la ubicación (ciudad) indicada
     * Requerimiento: “Filtrar por ciudad”
     * @param location Ciudad donde se localiza el producto.
     * @return Lista de productos en la ciudad especificada.
     */
    @Query("SELECT * FROM ProductEntity WHERE location = :location")
    suspend fun getProductsByCity(location: String): List<Product>

    /**
     * Busca productos filtrando por nombre de vendedor.
     * Realiza una búsqueda parcial utilizando `LIKE`.
     * Requerimiento: “Filtrar por vendedor”
     * @param name Parte del nombre del vendedor.
     * @return Lista de productos cuyo vendedor coincide parcial o totalmente con el nombre indicado.
     */
    @Query("SELECT * FROM ProductEntity WHERE producer LIKE '%' || :name || '%'")
    suspend fun searchProductsBySeller(name: String): List<Product>

    /**
     * Consulta SQL para obtener productos favoritos
     * Requerimiento: “Almacenar en favoritos los productos que el usuario tiene interés”
     */
    /*@Query("SELECT * FROM ProductEntity WHERE isFavorite = 1")
    fun getFavoriteProducts(): List<Product>*/

    /**
     * Consulta SQL para actualizar un producto en la tabla de la base de datos
     * Necesario para cambiar el estado de favorito, actualizar info, etc.
     * @param product Producto a actualizar.
     */
    @Update
    suspend fun updateProduct(product: Product)

    /**
     * Elimina un producto específico de la base de datos.
     * Para eliminar un producto (por ejemplo, limpiar favoritos antiguos o si el admin borra uno).
     * @param product Producto a eliminar.
     * */
    @Delete
    suspend fun deleteProduct(product: Product)

    /**
     * Inserta una lista de productos en la base de datos, reemplazando los existentes si hay conflictos.
     * Requerido cuando se sincronizan datos desde el servidor
     */
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(products: List<Product>)
}
package com.undef.localhandsbrambillafunes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.undef.localhandsbrambillafunes.data.entity.Product
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
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
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
     * Recupera todos los productos asociados a un determinado usuario (vendedor).
     *
     * Esta consulta obtiene todos los registros de la tabla `ProductEntity` cuyo campo `ownerId`
     * coincida con el identificador del usuario proporcionado. El resultado se devuelve como
     * un `Flow`, lo que permite observar los cambios en tiempo real (por ejemplo, si se agregan,
     * actualizan o eliminan productos del vendedor).
     *
     * ## Parámetros:
     * @param userId ID del usuario (vendedor) del cual se desean recuperar los productos publicados.
     *
     * ## Retorno:
     * @return Un `Flow` que emite listas actualizadas de productos pertenecientes al usuario indicado.
     *
     * ## Uso típico:
     * Este método es útil para mostrar en pantalla los productos que un usuario ha creado, como
     * en una sección de “Mis productos” o “Administrar publicaciones”.
     *
     * ## Ejemplo de consulta SQL generada:
     * ```sql
     * SELECT * FROM ProductEntity WHERE ownerId = :userId
     * ```
     */
    @Query("SELECT * FROM ProductEntity WHERE ownerId = :userId")
    fun getProductsByOwner(userId: Int): Flow<List<Product>>

    /**
     * Recupera un producto desde la base de datos en función de su identificador, como un flujo reactivo.
     *
     * Esta función retorna un [Flow] que emitirá el producto correspondiente al ID proporcionado, si existe.
     * Si no se encuentra ningún producto con el ID especificado, el flujo emitirá `null`.
     *
     * El uso de [Flow] permite observar cambios en la base de datos y reaccionar ante ellos de forma automática.
     *
     * @param id El identificador único del producto que se desea consultar.
     * @return Un [Flow] que emite una instancia de [Product] si se encuentra, o `null` en caso contrario.
     */
    @Query("SELECT * FROM ProductEntity WHERE id = :id LIMIT 1")
    fun getProductById(id: Int): Flow<Product?>

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
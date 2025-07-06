package com.undef.localhandsbrambillafunes.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.undef.localhandsbrambillafunes.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Consulta SQL para obtener todos los productos de la tabla
    @Query("SELECT * FROM ProductEntity")
    suspend fun getAllProducts(): Flow<List<Product>>

    // Consulta SQL para insertar un producto en la tabla
    @Insert
    suspend fun addProduct(product: Product): Long

    // Consulta SQL para obtener productos por categoría
    // Requerimiento: “Filtrar por categoría de productos”
    @Query("SELECT * FROM ProductEntity WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<Product>

    // Consulta SQL para obtener productos por ubicación
    // Requerimiento: “Filtrar por ciudad”
    @Query("SELECT * FROM ProductEntity WHERE location = :location")
    suspend fun getProductsByCity(location: String): List<Product>

    // Consulta SQL para obtener productos por nombre de vendedor
    // Requerimiento: “Filtrar por vendedor”
    @Query("SELECT * FROM ProductEntity WHERE producer LIKE '%' || :name || '%'")
    suspend fun searchProductsBySeller(name: String): List<Product>

    // Consulta SQL para obtener productos favoritos
    // Requerimiento: “Almacenar en favoritos los productos que el usuario tiene interés”
    /*@Query("SELECT * FROM ProductEntity WHERE isFavorite = 1")
    fun getFavoriteProducts(): List<Product>*/

    // Necesario para cambiar el estado de favorito, actualizar info, etc.
    @Update
    suspend fun updateProduct(product: Product)

    // Para eliminar un producto (por ejemplo, limpiar favoritos antiguos o si el admin borra uno).
    @Delete
    suspend fun deleteProduct(product: Product)

    // Requerido cuando se sincronizan datos desde el servidor
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(products: List<Product>)
}
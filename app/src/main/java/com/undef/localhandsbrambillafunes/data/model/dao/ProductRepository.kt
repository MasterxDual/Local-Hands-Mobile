package com.undef.localhandsbrambillafunes.data.repository

import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.data.model.db.ProductDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

//El flujo recomendado es el siguiente: DAO → Repository → ViewModel → UI (Compose)
//Utilizamos la arquitectura MVVM: Model-View-ViewModel
//Encargado de conectar el DAO con el ViewModel
/*Responsabilidad principal: Abstraer el acceso a múltiples fuentes de datos (local, remoto,
cache, etc.) y proporcionar una API limpia al resto de la aplicación.

¿Para qué sirve?
--> Intermediario entre la base de datos (Room) y el ViewModel.
--> Encapsula la lógica de acceso a datos y la forma en que se obtienen (por ejemplo, Room, Retrofit, DataStore, etc.).
--> Facilita pruebas unitarias porque se puede simular fácilmente.
--> Mejora la escalabilidad y mantenibilidad del código.*/
class ProductRepository(private val db: ProductDatabase) {
    fun getAllProducts(): Flow<List<Product>> {
        return db.productDao().getAllProducts()
    }

    suspend fun getProductsByCategory(category: String): List<Product> = withContext(Dispatchers.IO) {
        db.productDao().getProductsByCategory(category)
    }

    suspend fun insertProduct(product: Product): Long = withContext(Dispatchers.IO) {
        db.productDao().addProduct(product)
    }

    suspend fun getProductsByCity(location: String): List<Product> = withContext(Dispatchers.IO) {
        db.productDao().getProductsByCity(location)
    }

    suspend fun searchProductsBySeller(name: String): List<Product> = withContext(Dispatchers.IO) {
        db.productDao().searchProductsBySeller(name)
    }

    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        db.productDao().deleteProduct(product)
    }

    suspend fun insertAll(products: List<Product>) = withContext(Dispatchers.IO) {
        db.productDao().insertAll(products)
    }
}

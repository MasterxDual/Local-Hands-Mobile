package com.undef.localhandsbrambillafunes.data.repository

import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.data.model.db.ProductDatabase
import kotlinx.coroutines.flow.Flow

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
    fun getAllProducts(): Flow<List<Product>> = db.productDao().getAllProducts()

    fun getProductsByCategory(category: String): List<Product> =
        db.productDao().getProductsByCategory(category)

    fun insertProduct(product: Product): Long = db.productDao().addProduct(product)

    fun getProductsByCity(location: String): List<Product> = db.productDao().getProductsByCity(location)

    fun searchProductsBySeller(name: String): List<Product> = db.productDao().searchProductsBySeller(name)

    fun updateProduct(product: Product) = db.productDao().updateProduct(product)

    fun deleteProduct(product: Product) = db.productDao().deleteProduct(product)

    fun insertAll(products: List<Product>) = db.productDao().insertAll(products)
}

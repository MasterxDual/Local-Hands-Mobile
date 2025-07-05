package com.undef.localhandsbrambillafunes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.data.model.ProductProviderMigration
import com.undef.localhandsbrambillafunes.data.model.db.ProductApplication
import com.undef.localhandsbrambillafunes.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*Responsabilidad principal: Proporcionar datos a la UI y sobrevivir a cambios de configuración
(como rotaciones de pantalla).

¿Para qué sirve?
--> Recupera los datos desde el Repository y los expone a la UI (Compose) a través de State, LiveData, o StateFlow.
--> Contiene la lógica de presentación (no la lógica de negocio o de acceso a datos).
--> Permite separar la lógica de UI del código de acceso a datos, haciendo que la UI sea más sencilla y declarativa.*/
class ProductViewModel(repository: ProductRepository) : ViewModel() {
    private val repository = ProductRepository(ProductApplication.database)

    //Inicializamos la BD con los productos migrados
    init {
        viewModelScope.launch {
            // Verificamos si la BD ya tiene productos
            val hasProducts = repository.getAllProducts().first().isNotEmpty() //First se utiliza porque tenemos un Flow en la lista de productos que traemos
            if(!hasProducts) {
                //Si no tiene productos, se insertan los productos migrados
                val migratedProducts = ProductProviderMigration.getAllAsEntities()
                repository.insertAll(migratedProducts)
            }
        }
    }

    val products: StateFlow<List<Product>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun insertAll(products: List<Product>) = viewModelScope.launch {
        repository.insertAll(products)
    }
}
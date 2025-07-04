package com.undef.localhandsbrambillafunes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.data.model.db.ProductApplication
import com.undef.localhandsbrambillafunes.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*Responsabilidad principal: Proporcionar datos a la UI y sobrevivir a cambios de configuración
(como rotaciones de pantalla).

¿Para qué sirve?
--> Recupera los datos desde el Repository y los expone a la UI (Compose) a través de State, LiveData, o StateFlow.
--> Contiene la lógica de presentación (no la lógica de negocio o de acceso a datos).
--> Permite separar la lógica de UI del código de acceso a datos, haciendo que la UI sea más sencilla y declarativa.*/
class ProductViewModel(repository: ProductRepository) : ViewModel() {
    private val repository = ProductRepository(ProductApplication.database)

    var productList by mutableStateOf<List<Product>>(emptyList())
        private set

    fun loadAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productList = repository.getAllProducts()
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProduct(product)
            loadAllProducts()
        }
    }
}
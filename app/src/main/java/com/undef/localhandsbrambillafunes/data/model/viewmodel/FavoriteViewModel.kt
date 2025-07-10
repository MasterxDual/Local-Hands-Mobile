package com.undef.localhandsbrambillafunes.data.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.model.entities.Favorite
import com.undef.localhandsbrambillafunes.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la lógica relacionada con los productos favoritos.
 *
 * Esta clase se encarga de interactuar con el repositorio `FavoriteRepository` para
 * realizar operaciones como agregar un producto a la lista de favoritos de un usuario.
 * Utiliza `viewModelScope` para ejecutar tareas asincrónicas de forma segura dentro del ciclo de vida del ViewModel.
 *
 * @param favoriteRepository Instancia del repositorio que proporciona acceso a los datos de favoritos.
 */
class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    /**
     * Agrega un producto a la lista de favoritos de un usuario específico.
     *
     * Esta función lanza una corrutina en el `viewModelScope` para realizar la operación
     * de forma asincrónica, evitando bloquear el hilo principal.
     *
     * @param userId ID del usuario que desea agregar el producto a favoritos.
     * @param productId ID del producto que se desea marcar como favorito.
     */
    fun addFavorite(userId: Int, productId: Int) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(Favorite(userId = userId, productId = productId))
        }
    }
}
package com.undef.localhandsbrambillafunes.ui.viewmodel.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.dao.FavoriteDao
import com.undef.localhandsbrambillafunes.data.dao.FavoriteDao_Impl
import com.undef.localhandsbrambillafunes.data.entity.Favorite
import com.undef.localhandsbrambillafunes.data.entity.Product
import com.undef.localhandsbrambillafunes.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsable de gestionar la lógica relacionada con los productos favoritos.
 *
 * Esta clase se encarga de interactuar con el repositorio `FavoriteRepository` para
 * realizar operaciones como agregar un producto a la lista de favoritos de un usuario.
 * Utiliza `viewModelScope` para ejecutar tareas asincrónicas de forma segura dentro del ciclo de vida del ViewModel.
 *
 * @param favoriteRepository Instancia del repositorio que proporciona acceso a los datos de favoritos.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {



    /**
     * Agrega un producto a la lista de favoritos de un usuario específico.
     *
     * Esta función lanza una corrutina en el `viewModelScope` para realizar la operación
     * de forma asincrónica, evitando bloquear el hilo principal.
     *
     * @param productId ID del producto que se desea marcar como favorito.
     */
    fun addFavorite(productId: Int) {
        viewModelScope.launch {
            favoriteRepository.addFavoriteForCurrentUser(productId = productId)
        }
    }

    fun removeFavoriteByProductId(userId: Int, productId: Int) = viewModelScope.launch {
        favoriteRepository.removeFavorite(userId, productId)
    }


    fun getFavoritesForUser(userId: Int) = favoriteRepository.getFavoritesForUser(userId)
}
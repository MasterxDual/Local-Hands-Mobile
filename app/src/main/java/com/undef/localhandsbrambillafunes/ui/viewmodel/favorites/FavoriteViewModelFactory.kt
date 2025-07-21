package com.undef.localhandsbrambillafunes.ui.viewmodel.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.undef.localhandsbrambillafunes.data.repository.FavoriteRepository

/**
 * Fábrica personalizada para la creación de instancias de [FavoriteViewModel].
 *
 * Esta clase implementa la interfaz [ViewModelProvider.Factory] y se encarga de construir
 * el ViewModel [FavoriteViewModel] con los parámetros necesarios, como una instancia de [Application]
 * y un [FavoriteRepository], los cuales no pueden ser proporcionados directamente por el constructor predeterminado de ViewModel.
 *
 * Su uso es fundamental cuando el ViewModel requiere parámetros en su constructor, lo cual no es soportado
 * por defecto por el sistema de ViewModel de Android.
 *
 * @property application La instancia de [Application] utilizada por el ViewModel.
 * @property favoriteRepository El repositorio de favoritos que contiene la lógica de acceso a datos.
 */
class FavoriteViewModelFactory(
    private val application: Application,
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(application, favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.undef.localhandsbrambillafunes.data.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar el estado de sesión del usuario.
 *
 * Esta clase extiende de [AndroidViewModel] para tener acceso al contexto de la aplicación
 * y utiliza [MutableStateFlow] para mantener y exponer de forma reactiva el identificador del usuario.
 *
 * @param application instancia de la aplicación necesaria para [AndroidViewModel].
 */
class SessionViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Flujo interno y mutable que contiene el ID del usuario actualmente autenticado.
     * Es `null` si no hay un usuario autenticado.
     */
    private val _userId = MutableStateFlow<Int?>(null)

    /**
     * Flujo inmutable expuesto públicamente que representa el ID del usuario autenticado.
     * Las clases consumidoras pueden observar este flujo para reaccionar a los cambios de sesión.
     */
    val userId: StateFlow<Int?> = _userId

    /**
     * Inicia una sesión estableciendo el ID del usuario.
     *
     * @param id el identificador único del usuario que ha iniciado sesión.
     */
    fun login(id: Int) {
        viewModelScope.launch {
            _userId.value = id
        }
    }

    /**
     * Cierra la sesión actual eliminando el ID del usuario.
     */
    fun logout() {
        viewModelScope.launch {
            _userId.value = null
        }
    }
}
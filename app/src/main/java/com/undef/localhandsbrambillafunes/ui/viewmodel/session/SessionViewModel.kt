package com.undef.localhandsbrambillafunes.ui.viewmodel.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.entity.Product
import com.undef.localhandsbrambillafunes.data.entity.User
import com.undef.localhandsbrambillafunes.data.exception.NotAuthenticatedException
import com.undef.localhandsbrambillafunes.data.repository.AuthRepository
import com.undef.localhandsbrambillafunes.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsable de gestionar el estado de sesión del usuario autenticado.
 *
 * Esta clase extiende de [AndroidViewModel] para acceder al contexto de la aplicación cuando sea necesario.
 * Utiliza un [MutableStateFlow] para mantener el identificador del usuario autenticado y
 * exponerlo de forma reactiva a la interfaz de usuario u otras capas.
 *
 * Puede integrarse con un [UserRepository] si se desea validar o recuperar información adicional
 * del usuario autenticado.
 *
 * @param application Instancia de la aplicación requerida por [AndroidViewModel].
 * @param userRepository Repositorio opcional para validar información del usuario.
 */
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    /**
     * Obtenemos el id del usuario autenticado de la sesión actual
     * */
    suspend fun getCurrentUserId(): Int?  {
        return  authRepository.getCurrentUserId()
            ?: throw NotAuthenticatedException("User not logged in")
    }
}
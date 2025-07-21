package com.undef.localhandsbrambillafunes.ui.viewmodel.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.local.entities.User
import com.undef.localhandsbrambillafunes.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
class SessionViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    /**
     * Flujo interno y mutable que contiene el ID del usuario actualmente autenticado.
     * Es `null` si no hay una sesión activa
     * Es un flujo mutable interno (privado) que guarda el estado actual del ID del usuario logueado.
     * Puede cambiar con setUserId() o clearSession().
     */
    private val _userId = MutableStateFlow<Int?>(null)

    /**
     * Flujo inmutable expuesto públicamente que representa el ID del usuario autenticado.
     * Las clases consumidoras pueden observar este flujo para reaccionar a los cambios de sesión.
     * Es la exposición pública e inmutable de ese flujo. Lo usás cuando querés observar los
     * cambios en tiempo real (por ejemplo, en la UI con collectAsState() en Jetpack Compose).
     */
    val userId: StateFlow<Int?> = _userId

    /**
     * Flujo que representa el resultado del intento de inicio de sesión.
     */
    private val _loginResult = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginResult: StateFlow<LoginResult> = _loginResult

    /**
     * Establece el ID del usuario autenticado en la sesión actual.
     *
     * Este método debe llamarse luego de un inicio de sesión exitoso,
     * para guardar el identificador del usuario en el flujo de estado `_userId`,
     * permitiendo que otros componentes de la aplicación reaccionen
     * al cambio de estado de autenticación.
     *
     * @param id Identificador único del usuario autenticado.
     */
    fun setUserId(id: Int) {
        _userId.value = id
    }

    /**
     *
     * Útil para lógica puntual en ViewModel donde necesitás el valor actual
     * (por ejemplo, enviar el userId a un repositorio o guardarlo en otro lado).
     *
     * ## Cuando no conviene usarlo:
     *
     * En la UI, si querés mostrar algo que cambie dinámicamente al iniciar o cerrar sesión.
     *
     * Si querés observar si el usuario cambió y actualizar algo automáticamente (por ejemplo,
     * mostrar los productos favoritos del nuevo usuario logueado).
     */
    fun getUserId(): Int {
        return _userId.value ?: 0
    }

    /**
     * Limpia la sesión de usuario actual.
     *
     * Este método debe invocarse al cerrar sesión, eliminando
     * cualquier rastro del usuario autenticado al establecer
     * el valor de `_userId` como `null`. De esta forma, se
     * indica que no hay un usuario actualmente autenticado.
     */
    fun clearSession() {
        _userId.value = null
    }

    /**
     * Intenta autenticar a un usuario mediante correo electrónico y contraseña.
     *
     * @param email Correo electrónico ingresado por el usuario.
     * @param password Contraseña correspondiente.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)

            if (user == null) {
                _loginResult.value = LoginResult.UserNotFound
            } else if (user.password != password) {
                _loginResult.value = LoginResult.InvalidPassword
            } else {
                _userId.value = user.id
                _loginResult.value = LoginResult.Success(user.id)
            }
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param user Instancia del usuario a registrar.
     * @param onResult Callback que se llama con el ID generado por la base de datos.
    */
    fun registerUser(user: User, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val id = userRepository.insertUser(user)
            onResult(id)
        }
    }

    /**
     * Cierra la sesión eliminando el estado del usuario autenticado.
     */
    fun logout() {
        viewModelScope.launch {
            _userId.value = null
            _loginResult.value = LoginResult.Idle
        }
    }

    /**
     * Representa los posibles resultados del intento de inicio de sesión.
     */
    sealed class LoginResult {
        object Idle : LoginResult()
        data class Success(val userId: Int) : LoginResult()
        object UserNotFound : LoginResult()
        object InvalidPassword : LoginResult()
    }
}
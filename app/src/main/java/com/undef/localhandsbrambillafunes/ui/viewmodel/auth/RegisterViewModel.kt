package com.undef.localhandsbrambillafunes.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undef.localhandsbrambillafunes.data.entity.User
import com.undef.localhandsbrambillafunes.data.repository.AuthRepository
import com.undef.localhandsbrambillafunes.service.EmailService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado UI para registro
 *
 * @property isLoading Indica operación en curso
 * @property isSuccess Indica registro exitoso
 * @property errorMessage Mensaje de error
 * @property needsVerification Indica si se requiere verificación
 * @property userEmail Email del usuario registrado
 * @property tempUser Usuario temporal durante verificación
 */
data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val needsVerification: Boolean = false,
    val userEmail: String? = null,
    val tempUser: User? = null // Alamacenar usuario temporalmente
)

/**
 * ViewModel para registro de usuarios con verificación por email
 *
 * @property authRepository Repositorio de autenticación
 * @property emailService Servicio de envío de emails
 *
 * @method prepareRegistration Pre-valida y envía código de verificación
 * @method sendVerificationCode Genera y envía código al email
 * @method verifyCode Valida código ingresado por usuario
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val emailService: EmailService
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    private var tempUser: User? = null  // Almacenar usuario temporalmente

    private var verificationCode: String? = null  // Almacenar el código generado

    // Para menjar los reintentos de codigo de verificacion
    private var verificationAttempts = 0
    private val MAX_ATTEMPTS = 3

    /**
     * Prepara registro: verifica email y almacena usuario temporal
     *
     * @param name Nombre usuario
     * @param lastName Apellido usuario
     * @param email Email usuario
     * @param password Contraseña
     * @param phone Teléfono
     * @param address Dirección
     */
    fun prepareRegistration(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        address: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            // 1. Verificar si el email ya existe
            authRepository.isEmailExists(email)
                .onSuccess { exists ->
                    if (exists) {
                        // Manejo email existente
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "El email ya está registrado"
                        )
                    } else {
                        // 2. Guardar usuario temporalmente (NO en base de datos)
                        tempUser = User(
                            name = name,
                            lastName = lastName,
                            email = email,
                            password = password,
                            phone = phone,
                            address = address,
                            isEmailVerified = false
                        )

                        // 3. Enviar código de verificación
                        sendVerificationCode(email)
                    }
                }
                // Manejo de errores
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
        }
    }

    /**
     * Envía código de verificación por email
     *
     * @param email Email destino
     */
    fun sendVerificationCode(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            authRepository.generateVerificationCode(email)
                .onSuccess { code ->
                    // Guardar el código generado en memoria
                    verificationCode = code

                    emailService.sendVerificationEmail(email, code)
                        .onSuccess {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                needsVerification = true,
                                userEmail = email
                            )
                        }
                }
        }
    }

    /**
     * Verifica código ingresado por usuario
     *
     * @param email Email del usuario
     * @param code Código ingresado
     */
    fun verifyCode(email: String, code: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            verificationCode?.let { expectedCode ->
                authRepository.verifyCode(email, code, expectedCode)
                    .onSuccess {
                        // Resetear intentos en éxito
                        verificationAttempts = 0

                        tempUser?.let { user ->
                            authRepository.registerUser(user.copy(isEmailVerified = true))
                                .onSuccess {
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        needsVerification = false
                                    )
                                }
                        }
                    }
                    .onFailure { exception ->
                        // Manejo de intentos fallidos
                        verificationAttempts++

                        if (verificationAttempts >= MAX_ATTEMPTS) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Demasiados intentos. Por favor solicita un nuevo código",
                                needsVerification = false
                            )
                            verificationAttempts = 0
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Código incorrecto. Intentos restantes: ${MAX_ATTEMPTS - verificationAttempts}"
                            )
                        }
                    }
            } ?: run {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error: No se encontró código de verificación"
                )
            }
        }
    }

    /**
     * Limpia estado y datos temporales
     */
    fun clearState() {
        _uiState.value = RegisterState()
        // Limpiar datos temporales
        tempUser = null  // Limpiar usuario temporal
    }

    /**
     * Limpia tabla de usuarios (SOLO PRUEBAS)
     */
    fun clearUsers() {
        viewModelScope.launch {
            authRepository.clearUsersTable()
        }
    }
}
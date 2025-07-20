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
 * Estado UI para recuperación de contraseña
 *
 * @property isLoading Indica operación en curso
 * @property isSuccess Indica éxito parcial
 * @property errorMessage Mensaje de error
 * @property currentStep Paso actual del flujo (1-3)
 * @property userEmail Email del usuario
 * @property generatedCode Código generado
 * @property shouldNavigateToLogin Indica si navegar a login
 */
data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false, // Para exitos parciales
    val errorMessage: String? = null,
    val currentStep: Int = 1,
    val userEmail: String? = null,
    val generatedCode: String? = null,
    val shouldNavigateToLogin: Boolean = false // Para controlar navegación final
)

/**
 * ViewModel para recuperación de contraseña
 *
 * @property authRepository Repositorio de autenticación
 * @property emailService Servicio de envío de emails
 *
 * @method sendResetCode Envía código de reset al email
 * @method verifyResetCode Valida código de 4 dígitos
 * @method resetPassword Actualiza contraseña en base de datos
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val emailService: EmailService
) : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordState())
    val uiState: StateFlow<ForgotPasswordState> = _uiState.asStateFlow()

    // Para menjar los reintentos de codigo de verificacion
    private var verificationAttempts = 0
    private val MAX_ATTEMPTS = 3

    /**
     * Envía código de reset al email
     *
     * @param email Email del usuario
     */
    fun sendResetCode(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                userEmail = email  // Guardar email inmediatamente
            )

            // Verificar si el email existe
            authRepository.isEmailExists(email)
                .onSuccess { exists ->
                    if (exists) {
                        // Generar y enviar código
                        authRepository.generateVerificationCode(email)
                            .onSuccess { code ->
                                // GUARDAR EL CÓDIGO EN EL ESTADO
                                _uiState.value = _uiState.value.copy(generatedCode = code)

                                emailService.sendVerificationEmail(email, code)
                                    .onSuccess {
                                        _uiState.value = _uiState.value.copy(
                                            isLoading = false,
                                            isSuccess = true,
                                            currentStep = 2,
                                            userEmail = email
                                        )
                                    }
                                    // Manejo errores envío email
                                    .onFailure { exception ->
                                        _uiState.value = _uiState.value.copy(
                                            isLoading = false,
                                            isSuccess = false,
                                            errorMessage = "Error enviando email: ${exception.message}"
                                        )
                                    }
                            }
                            // Manejo errores generación código
                            .onFailure { exception ->
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    isSuccess = false,
                                    errorMessage = exception.message
                                )
                            }
                    } else {
                        // Manejo email no registrado
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = false,
                            errorMessage = "El email no está registrado"
                        )
                    }
                }
                // Manejo errores verificación email
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = exception.message
                    )
                }
        }
    }

    /**
     * Verifica código de reset
     *
     * @param email Email del usuario
     * @param code Código ingresado
     */
    fun verifyResetCode(email: String, code: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            authRepository.verifyResetCode(email, code)
                .onSuccess {
                    // Resetear intentos en éxito
                    verificationAttempts = 0
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        currentStep = 3
                    )
                }
                .onFailure { exception ->
                    verificationAttempts++

                    // Manejo intentos fallidos
                    if (verificationAttempts >= MAX_ATTEMPTS) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Demasiados intentos. Por favor solicita un nuevo código",
                        )
                        verificationAttempts = 0
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Código incorrecto. Intentos restantes: ${MAX_ATTEMPTS - verificationAttempts}"
                        )
                    }
                }
        }
    }

    /**
     * Actualiza contraseña en base de datos
     *
     * @param email Email del usuario
     * @param newPassword Nueva contraseña
     */
    fun resetPassword(email: String, newPassword: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            println("DEBUG: Intentando actualizar contraseña para $email") // Eliminar en produccion

            authRepository.updatePassword(email, newPassword)
                .onSuccess { success ->
                    if (success) {
                        println("DEBUG: Contraseña actualizada exitosamente para $email")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            shouldNavigateToLogin = true
                        )
                    } else {
                        // Manejo error actualización
                        println("DEBUG: Falló la actualización de contraseña para $email")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "No se pudo actualizar la contraseña"
                        )
                    }
                }
                // Manejo errores
                .onFailure { exception ->
                    println("DEBUG: Error actualizando contraseña: ${exception.message}")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
        }
    }

    /**
     * Limpia estado de la UI
     */
    fun clearState() {
        _uiState.value = ForgotPasswordState(
            shouldNavigateToLogin = false // Mantener en false al resetear
        )
    }
}
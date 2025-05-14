package com.undef.localhandsbrambillafunes.ui.screens.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


/**
 * Pantalla de recuperación de contraseña con flujo de 3 pasos:
 * 1. Ingreso de email
 * 2. Verificación de código (cualquier código de 4 dígitos)
 * 3. Creación de nueva contraseña
 *
 * @param navController Controlador de navegación para manejar el flujo entre pantallas
 */
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    // --- ESTADOS ---
    // Estados para los campos del formulario
    var email by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    // Control del paso actual en el flujo (1-3)
    var currentStep by remember { mutableStateOf(1) }

    // Control de visibilidad de la contraseña
    var showPassword by remember { mutableStateOf(false) }

    // --- VALIDACIONES ---
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isCodeValid = verificationCode.length == 4  // Solo requiere 4 dígitos (sin validación de código real)
    val isPasswordValid = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$").matches(newPassword)  // 8+ chars, 1 mayúscula, 1 número
    val doPasswordsMatch = newPassword == repeatPassword && repeatPassword.isNotBlank()

    // --- ESTRUCTURA PRINCIPAL ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242424))  // Fondo oscuro
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)  // Centra el contenido vertical y horizontalmente
                .padding(16.dp)
                .verticalScroll(rememberScrollState())  // Permite scroll si el contenido es largo
        ) {
            // Tarjeta que contiene el formulario completo
            Card(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(12.dp),  // Esquinas redondeadas
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    // Título de la pantalla
                    Text(
                        text = "Recuperar Contraseña",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Controlador del flujo por pasos
                    when (currentStep) {
                        1 -> EmailStep(
                            email = email,
                            isEmailValid = isEmailValid,
                            onEmailChange = { email = it },
                            onSendClick = { currentStep = 2 }
                        )
                        2 -> CodeVerificationStep(
                            code = verificationCode,
                            onCodeChange = { verificationCode = it },
                            onVerifyClick = { currentStep = 3 },
                            isCodeValid = isCodeValid
                        )
                        3 -> NewPasswordStep(
                            newPassword = newPassword,
                            repeatPassword = repeatPassword,
                            showPassword = showPassword,
                            onNewPasswordChange = { newPassword = it },
                            onRepeatPasswordChange = { repeatPassword = it },
                            onTogglePasswordVisibility = { showPassword = !showPassword },
                            isPasswordValid = isPasswordValid,
                            doPasswordsMatch = doPasswordsMatch,
                            onSubmit = { navController.popBackStack() }  // Vuelve atrás al completar
                        )
                    }
                }
            }
        }
    }
}

/**
 * Paso 1: Ingreso de email para recuperación
 *
 * @param email Valor actual del campo email
 * @param isEmailValid Indica si el email tiene formato válido
 * @param onEmailChange Callback cuando cambia el texto del email
 * @param onSendClick Callback cuando se presiona el botón de enviar
 */
@Composable
private fun EmailStep(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    // Instrucciones para el usuario
    Text(
        text = "Ingrese su correo electrónico para continuar con la recuperación.",
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Campo de texto para email
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedLabelColor = if (isEmailValid) Color.Green else Color.Red,  // Feedback visual
            focusedIndicatorColor = if (isEmailValid) Color.Green else Color.Red
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botón para continuar al siguiente paso
    Button(
        onClick = onSendClick,
        enabled = isEmailValid,  // Solo habilitado si el email es válido
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("ENVIAR CÓDIGO")
    }
}

/**
 * Paso 2: Verificación de código de 4 dígitos
 *
 * @param code Valor actual del campo de código
 * @param onCodeChange Callback cuando cambia el texto del código
 * @param onVerifyClick Callback cuando se presiona el botón de validar
 * @param isCodeValid Indica si el código tiene exactamente 4 dígitos
 */
@Composable
private fun CodeVerificationStep(
    code: String,
    onCodeChange: (String) -> Unit,
    onVerifyClick: () -> Unit,
    isCodeValid: Boolean
) {
    // Instrucciones para el usuario
    Text(
        text = "Ingrese el código de 4 dígitos que recibió en su correo.",
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Campo de texto para el código (solo acepta números)
    OutlinedTextField(
        value = code,
        onValueChange = {
            if (it.length <= 4 && it.all { char -> char.isDigit() }) {  // Solo permite 4 dígitos
                onCodeChange(it)
            }
        },
        label = { Text("Código") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedLabelColor = if (isCodeValid) Color.Green else Color.Red,  // Feedback visual
            focusedIndicatorColor = if (isCodeValid) Color.Green else Color.Red
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botón para continuar al siguiente paso
    Button(
        onClick = onVerifyClick,
        enabled = isCodeValid,  // Solo habilitado con 4 dígitos
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("VALIDAR CÓDIGO")
    }
}

/**
 * Paso 3: Creación de nueva contraseña
 *
 * @param newPassword Valor actual del campo nueva contraseña
 * @param repeatPassword Valor actual del campo repetir contraseña
 * @param showPassword Indica si la contraseña es visible o oculta
 * @param onNewPasswordChange Callback cuando cambia la nueva contraseña
 * @param onRepeatPasswordChange Callback cuando cambia la repetición de contraseña
 * @param onTogglePasswordVisibility Callback para alternar visibilidad
 * @param isPasswordValid Indica si la contraseña cumple los requisitos
 * @param doPasswordsMatch Indica si ambas contraseñas coinciden
 * @param onSubmit Callback cuando se presiona el botón de enviar
 */
@Composable
private fun NewPasswordStep(
    newPassword: String,
    repeatPassword: String,
    showPassword: Boolean,
    onNewPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    isPasswordValid: Boolean,
    doPasswordsMatch: Boolean,
    onSubmit: () -> Unit
) {
    // Instrucciones para el usuario
    Text(
        text = "Ingrese su nueva contraseña",
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Campo para nueva contraseña (usando componente personalizado)
    PasswordField(
        label = "Nueva contraseña",
        value = newPassword,
        visible = showPassword,
        isValid = isPasswordValid,
        onChange = onNewPasswordChange
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Campo para repetir contraseña (usando componente personalizado)
    RepeatPasswordField(
        label = "Repetir contraseña",
        originalPassword = newPassword,
        repeatPassword = repeatPassword,
        visible = showPassword,
        onChange = onRepeatPasswordChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botón para completar el proceso
    Button(
        onClick = onSubmit,
        enabled = isPasswordValid && doPasswordsMatch,  // Solo habilitado si todo es válido
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("CAMBIAR CONTRASEÑA")
    }
}
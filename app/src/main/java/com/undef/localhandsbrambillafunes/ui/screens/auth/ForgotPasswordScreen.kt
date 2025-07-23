package com.undef.localhandsbrambillafunes.ui.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.ui.components.ErrorDialog
import com.undef.localhandsbrambillafunes.ui.components.LoadingDialog
import com.undef.localhandsbrambillafunes.ui.components.PasswordField
import com.undef.localhandsbrambillafunes.ui.components.RepeatPasswordField
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens
import com.undef.localhandsbrambillafunes.ui.viewmodel.auth.ForgotPasswordViewModel

/**
 * Pantalla de recuperación de contraseña con flujo de 3 pasos:
 * 1. Ingreso de email
 * 2. Verificación de código
 * 3. Creación de nueva contraseña
 *
 * @param navController Controlador de navegación para manejar el flujo entre pantallas
 *
 * @property viewModel ViewModel que maneja la lógica de negocio
 * @property uiState Estado actual de la UI (paso actual, errores, carga)
 * @property context Contexto local para mostrar Toasts
 */
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Observar éxito para navegar
    LaunchedEffect(uiState.shouldNavigateToLogin) {
        if (uiState.shouldNavigateToLogin) {
            // Mostrar mensaje de éxito antes de navegar
            Toast.makeText(context, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()

            navController.popBackStack()
            navController.navigate(AppScreens.LoginScreen.route)
            viewModel.clearState()
        }
    }

    // Mostrar diálogos
    if (uiState.isLoading) {
        LoadingDialog(isLoading = true)
    }

    uiState.errorMessage?.let { error ->
        ErrorDialog(errorMessage = error) {
            viewModel.clearState()
        }
    }

    Box(Modifier.fillMaxSize().background(Color(0xFF242424))) {
        Column(Modifier.align(Alignment.Center).padding(16.dp)) {
            Card(Modifier.padding(8.dp), shape = RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(24.dp).fillMaxWidth()) {
                    Text("Recuperar Contraseña", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))

                    when (uiState.currentStep) {
                        // Paso 1: Ingreso de email
                        1 -> {
                            var email by remember { mutableStateOf("") }
                            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

                            Text("Ingrese su correo electrónico para continuar.")
                            Spacer(Modifier.height(16.dp))

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Email") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedLabelColor = if (isEmailValid) Color.Green else Color.Red
                                )
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.sendResetCode(email) },
                                enabled = isEmailValid,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("ENVIAR CÓDIGO")
                            }
                        }
                        // Paso 2: Verificación de código
                        2 -> {
                            var code by remember { mutableStateOf("") }
                            val isCodeValid = code.length == 4

                            Text("Ingrese el código de 4 dígitos recibido.")
                            Spacer(Modifier.height(16.dp))

                            OutlinedTextField(
                                value = code,
                                onValueChange = { if (it.length <= 4 && it.all { c -> c.isDigit() }) code = it },
                                label = { Text("Código") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedLabelColor = if (isCodeValid) Color.Green else Color.Red
                                )
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    uiState.userEmail?.let { email ->
                                        viewModel.verifyResetCode(email, code) // Usa la nueva función
                                    }
                                },
                                enabled = isCodeValid,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("VALIDAR CÓDIGO")
                            }
                        }
                        // Paso 3: Nueva contraseña
                        3 -> {
                            var newPassword by remember { mutableStateOf("") }
                            var repeatPassword by remember { mutableStateOf("") }
                            var showPassword by remember { mutableStateOf(false) }

                            val isPasswordValid = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$").matches(newPassword)
                            val doPasswordsMatch = newPassword == repeatPassword && repeatPassword.isNotBlank()

                            Text("Ingrese su nueva contraseña")
                            Spacer(Modifier.height(16.dp))

                            PasswordField(
                                label = "Nueva contraseña",
                                value = newPassword,
                                visible = showPassword,
                                isValid = isPasswordValid,
                                onChange = { newPassword = it },
                                onToggleVisibility = { showPassword = !showPassword } // Añadir esto
                            )

                            Spacer(Modifier.height(8.dp))

                            RepeatPasswordField(
                                label = "Repetir contraseña",
                                originalPassword = newPassword,
                                repeatPassword = repeatPassword,
                                visible = showPassword,
                                onChange = { repeatPassword = it },
                                onToggleVisibility = { showPassword = !showPassword } // Añadir esto

                            )

                            // Agregar mensajes de error
                            if (!isPasswordValid && newPassword.isNotBlank()) {
                                Text(
                                    "La contraseña debe tener 8+ caracteres, 1 mayúscula y 1 número",
                                    color = Color.Red,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            if (!doPasswordsMatch && repeatPassword.isNotBlank()) {
                                Text(
                                    "Las contraseñas no coinciden",
                                    color = Color.Red,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            // Boton
                            Button(
                                onClick = {
                                    uiState.userEmail?.let { email ->
                                        viewModel.resetPassword(email, newPassword)
                                    }
                                },
                                enabled = isPasswordValid && doPasswordsMatch,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("CAMBIAR CONTRASEÑA")
                            }
                        }
                    }
                }
            }
        }
    }
}
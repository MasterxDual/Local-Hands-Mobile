package com.undef.localhandsbrambillafunes.ui.screens.auth

//Imports necesarios para Composables, UI, estado, imágenes, etc.
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.R
import com.undef.localhandsbrambillafunes.ui.components.ErrorDialog
import com.undef.localhandsbrambillafunes.ui.components.LoadingDialog
import com.undef.localhandsbrambillafunes.ui.components.PasswordField
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens
import com.undef.localhandsbrambillafunes.ui.viewmodel.auth.LoginViewModel

/**
 * Pantalla de inicio de sesión con validación de credenciales
 *
 * @param navController Controlador de navegación para redireccionar
 *
 * @property viewModel ViewModel para autenticación
 * @property uiState Estado de la operación (carga, éxito, errores)
 * @property email Estado del campo email
 * @property password Estado del campo contraseña
 * @property showPassword Visibilidad de la contraseña
 */
@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
//    val context = LocalContext.current

    // Observar éxito para navegar
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
            navController.navigate(AppScreens.HomeScreen.route)
        }
    }

    // Mostrar dialogos
    if (uiState.isLoading) {
        LoadingDialog(isLoading = true)
    }

    uiState.errorMessage?.let { error ->
        ErrorDialog(errorMessage = error) {
            viewModel.clearState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242424))
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Card(
                Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    RowImage()

                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var showPassword by remember { mutableStateOf(false) }

                    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    val isPasswordValid = password.length >= 6

                    // Campo Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(
                            focusedLabelColor = if (isEmailValid) Color.Green else Color.Red,
                            focusedIndicatorColor = if (isEmailValid) Color.Green else Color.Red
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )

                    // Campo Contraseña (usando componentes reutilizables)
                    PasswordField(
                        label = "Contraseña",
                        value = password,
                        visible = showPassword,
                        isValid = isPasswordValid,
                        onChange = { password = it },
                        onToggleVisibility = { showPassword = !showPassword }, // Callback añadido
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )

                    // Boton de Login
                    Button(
                        onClick = { viewModel.login(email, password) },
                        enabled = isEmailValid && isPasswordValid,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text("Iniciar Sesion")
                    }

                    // Navegacion
                    RowForgottenPassword(navController)
                    RowRegister(navController)
                }
            }
        }
    }
}

/**
 * Componente: Logo de la aplicación
 */
@Composable
fun RowImage() {
    //Elementos estáran alineados de forma horizontal
    // Fila que contiene el logo centrado
    Row(Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        //Definimos nuestra imagen a mostrar
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id = R.drawable.localhandslogo),
            contentDescription = "Imagen de la aplicación"
        )
    }
}

/**
 * Componente: Enlace a registro
 */
@Composable
fun RowRegister(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿No tienes Cuenta? Registrate",
            color = Color.Blue,
            modifier = Modifier.clickable() {
                navController.navigate(route = AppScreens.RegisterScreen.route)
            }
        )
    }
}

/**
 * Componente: Enlace a recuperación de contraseña
 */
@Composable
fun RowForgottenPassword(navController: NavController) {
    Row(Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.End) {
        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color.Blue,
//            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(route = AppScreens.ForgotPasswordScreen.route)
            }
                .padding(8.dp)
        )
    }
}


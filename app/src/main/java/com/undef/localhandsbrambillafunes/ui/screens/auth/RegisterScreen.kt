package com.undef.localhandsbrambillafunes.ui.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.data.local.entities.User
import com.undef.localhandsbrambillafunes.ui.viewmodel.session.SessionViewModel
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens

@Composable
fun RegisterScreen(navController: NavController, sessionViewModel: SessionViewModel) {
    val context = LocalContext.current

    //Variables que se utilizarán para ingresar los datos
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    //Variable que se utiliza para hacer la contraseña visible
    var showPassword by remember { mutableStateOf(false) }

    //Variables para validar los datos ingresados
    val isNameValid = name.length >= 4
    val isLastNameValid = lastName.length >= 4
    val isAddressValid = address.length >= 4
    val isPhoneValid = phone.length >= 8
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$").matches(password)
    val doPasswordsMatch = password == repeatPassword && repeatPassword.isNotBlank()

    //Variable que se utilizará para verificar que todo el formulario haya sido ingresado
    val isFormComplete = isNameValid && isLastNameValid &&
            isEmailValid && isPasswordValid && doPasswordsMatch &&
            phone.isNotBlank() && isAddressValid

    //Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242424))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState() //Permite que toda la columna del formulario de registro sea desplazable con un scroll si ocupa mayor altura que la de la pantalla completa
                )
        ) {
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp)) //Agrega un espacio vertical vacío entre los elementos de la interfaz. Separa elementos de la interfaz

                    //Campos de ingreso de datos de registro que se validarán
                    ValidatedInputField("Nombre", name, isNameValid) { name = it }
                    ValidatedInputField("Apellido", lastName, isLastNameValid) { lastName = it }
                    EmailField("Email", email, isEmailValid) { email = it }
                    PasswordField("Contraseña", password, showPassword, isPasswordValid) { password = it }
                    RepeatPasswordField(
                        label = "Repetir Contraseña",
                        originalPassword = password,
                        repeatPassword = repeatPassword,
                        visible = showPassword
                    ) { repeatPassword = it }
                    PhoneField("Teléfono", phone, isPhoneValid) { phone = it }
                    ValidatedInputField("Domicilio", address, isAddressValid) { address = it }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            // Crear UserEntity con los datos del formulario
                            val newUser = User(
                                name = name,
                                lastName = lastName,
                                email = email,
                                password = password
                            )
                            // Registrar el usuario usando el ViewModel
                            sessionViewModel.registerUser(newUser) { id ->
                                if (id > 0) {
                                    Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                                    sessionViewModel.setUserId(id.toInt()) //Guardamos el userId para pasarlo a las demás pantallas
                                    navController.navigate(route = AppScreens.LoginScreen.route)
                                } else {
                                    Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        enabled = isFormComplete, //Verifica que el formulario haya sido totalmente completado
                        modifier = Modifier.fillMaxWidth() //Ocupa todo el ancho de la pantalla
                    ) {
                        Text("Registrar")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "¿Ya tienes cuenta? Inicia sesión",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable { navController.navigate(route = AppScreens.LoginScreen.route) }
                            .align(Alignment.CenterHorizontally) //Centra horizontalmente el texto con vínculo que lleva hacia la pantalla de login
                    )
                }
            }
        }
    }
}

@Composable
fun PhoneField(
    label: String,
    value: String,
    isValid: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange: (String) -> Unit //Verifica en tiempo real el valor ingresado en el teléfono, necesario para hacer validaciones. Es una función lambda
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) }, //Campo de texto que se mostrará en el input antes de poder ingresarle el input
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        singleLine = true,
        /*Agrega colores rojo y verde para mostrar cuando el dato ingresado sea válido o no*/
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        )
    )
}

@Composable
fun EmailField(
    label: String,
    value: String,
    isValid: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        )
    )
}


@Composable
fun ValidatedInputField(
    label: String,
    value: String,
    isValid: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        )
    )
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    visible: Boolean,
    isValid: Boolean,
    onChange: (String) -> Unit
) {
    var visibility by remember { mutableStateOf(visible) }
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        ),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(), //Controla cómo se muestra el texto ingresado en el campo de contraseña
        /*Define el ícono que aparece al final del campo de texto de la contraseña (el "ojo" que permite mostrar u ocultar el texto).*/
        trailingIcon = {
            IconButton(onClick = { visibility = !visibility }) {
                Icon(
                    imageVector = if (visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Ver/Ocultar"
                )
            }
        }
    )
}

@Composable
fun RepeatPasswordField(
    label: String,
    originalPassword: String,
    repeatPassword: String,
    visible: Boolean,
    onChange: (String) -> Unit
) {
    var visibility by remember { mutableStateOf(visible) }
    val doPasswordsMatch = repeatPassword == originalPassword && repeatPassword.isNotBlank() //Verifica que la contraseña ingresada sea idéntica a la ingresada previamente

    Column {
        OutlinedTextField(
            value = repeatPassword,
            onValueChange = onChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = if (doPasswordsMatch) Color.Green else Color.Red,
                focusedIndicatorColor = if (doPasswordsMatch) Color.Green else Color.Red,
                unfocusedIndicatorColor = if (doPasswordsMatch) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
            ),
            visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { visibility = !visibility }) {
                    Icon(
                        imageVector = if (visibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Ver/Ocultar"
                    )
                }
            }
        )
        /*Muestra un campo de texto en rojo debajo del campo de contraseña ingresado si no son idénticas las dos claves ("Las contraseñas no coinciden")*/
        if (!doPasswordsMatch && repeatPassword.isNotBlank()) {
            Text(
                text = "Las contraseñas no coinciden",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}


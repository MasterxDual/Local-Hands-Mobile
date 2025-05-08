package com.undef.localhandsbrambillafunes.ui.screens.auth

//Imports necesarios para Composables, UI, estado, imágenes, etc.
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.undef.localhandsbrambillafunes.R

//Para mostrar como quedaría nuestro login en una interfaz
//Vista previa de la pantalla de login para diseño
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen() {
    val context = LocalContext.current

    //Variables que se utilizarán para ingresar los datos
    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var visiblePassword by remember { mutableStateOf(false) }

    /*Parte visual de la pantalla*/
    //Caja que ocupará todo el tamaño de la pantalla
    //Contenedor principal de la pantalla
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFb5e48c))) {
        //Alineamos de forma vertical dentro de la caja, columnas centradas, y que tome todo el ancho posible
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            //Tarjeta para agrupar los componentes visuales
            //Tarjeta que contendrá la imagen y los elementos para las variables creadas arriba
            Card(Modifier.padding(12.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                //Elementos internos se alinearán de forma vertical
                Column(Modifier.padding(16.dp)) {
                    //Definimos nuestro elemento de imagen, para el email, para la contraseña y para el botón
                    RowImage()
                    RowEmail(
                        email = email,
                        emailChange = {
                            email = it //it es lo que recibimos en el momento, el input
                            // Validación del email usando expresiones regulares
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches() //Validamos si el email es correcto, devuelve un boolean
                        },
                        isValidEmail //Se pasa la funcion lambda que devolverá un booleano, en la misma función
                    )
                    RowPassword(
                        password = password,
                        passwordChange = {
                            password = it //Verificamos que la contraseña que ingresa el usuario sea mayor a 6 caracteres
                            isValidPassword = password.length >= 6
                        },
                        //Esto pasa el estado actual (true/false) que indica si la contraseña debe mostrarse o no, al componente RowPassword, que lo usa para aplicar o no la transformación visual (PasswordVisualTransformation).
                        visiblePassword = visiblePassword,
                        visiblePasswordChange = { visiblePassword = !visiblePassword }, //Funcion lambda, Esto se dispara al hacer clic en el ícono del ojo, lo que alterna entre mostrar y ocultar el texto.
                        isValidPassword = isValidPassword //Pasa el estado de validez de la contraseña (mínimo 6 caracteres) al campo para aplicar colores de validación visual (verde si es válida, rojo si no).
                    )
                    RowButtonLogin(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword
                    )
                }
            }
        }
    }
}

@Composable
//Componente que llevará la fila
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

@Composable
fun RowEmail(
    email: String,
    emailChange: (String) -> Unit, //Función lambda: para saber que hacer cuando va a cambiar el texto conforme lo vamos escribiendo en el elemento
    isValid: Boolean
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = emailChange, //Que queremos que haga cuando se escribe un elemento en ese campo. Es decir, la accion que queremos que se haga
            label = { Text(text = "Email") }, //Etiqueta que mostrará en el campo
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), //Permite mejorar la experiencia del usuario al mostrar un teclado adecuado al tipo de dato que se espera
            maxLines = 1, //Una sola línea
            singleLine = true, //Única línea
            //Definimos los colores cuando interactuemos con la fila del email
            //Colores condicionales para indicar validez del campo
            colors = TextFieldDefaults.colors(
                focusedLabelColor = if (isValid) Color.Green else Color.Red,
                focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
                unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f) //Definimos el color cuando no estamos enfocando el email (0.6f indica 60% opaco y 40% transparente
            )
        )
    }
}

@Composable
fun RowPassword(
    password: String,
    passwordChange: (String) -> Unit,
    visiblePassword: Boolean,
    visiblePasswordChange: () -> Unit,
    isValidPassword: Boolean
) {
    Row(Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = password,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            // Icono que permite alternar la visibilidad de la contraseña
            trailingIcon = { //Para mostrar u ocultar la contraseña
                val image = if(visiblePassword) { //Icono que queremos que muestre la contraseña, es el ojo para ocultar o ver la contraseña
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                //Definimos el icono que queremos mostrar
                IconButton(
                    onClick = visiblePasswordChange
                ) {
                    //Hacemos la referencia al icono que queremos mostrar
                    Icon(
                        imageVector = image,
                        contentDescription = "Ver contraseña")
                }
            },
            //Transformacion para que se visualice o se oculte el texto de ese componente
            visualTransformation = if(visiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            //Definimos los colores cuando interactuemos con la fila del email
            // Colores del campo basados en validez
            colors = TextFieldDefaults.colors(
                focusedLabelColor = if (isValidPassword) Color.Green else Color.Red,
                focusedIndicatorColor = if (isValidPassword) Color.Green else Color.Red,
                unfocusedIndicatorColor = if (isValidPassword) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f) //Definimos el color cuando no estamos enfocando el email (0.6f indica 60% opaco y 40% transparente
            )
        )
    }
}

@Composable
fun RowButtonLogin(
    context: Context, //Para pasarle el valor al toast cuando hagamos el login correcto
    isValidEmail: Boolean,
    isValidPassword: Boolean
) {
    Row(Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        // Botón habilitado solo cuando las validaciones son correctas
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { login(context) },
            enabled = isValidEmail && isValidPassword //Cuando se cumplan las validaciones el boton debe validarse
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}

// Función simulada para login
fun login(context: Context) {
    Toast.makeText(context, "Login falso", Toast.LENGTH_SHORT).show()
}
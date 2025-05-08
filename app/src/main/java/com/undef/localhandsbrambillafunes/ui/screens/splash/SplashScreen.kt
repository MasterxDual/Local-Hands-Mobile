package com.undef.localhandsbrambillafunes.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.undef.localhandsbrambillafunes.R
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens
import kotlinx.coroutines.delay

/**
 * Pantalla de inicio (Splash Screen) con fondo negro.
 *
 * @param navController Controlador de navegación para manejar transiciones entre pantallas.
 *
 * Funcionalidades clave:
 * - Muestra el logo de la aplicación
 * - Permanece visible durante 5 segundos
 * - Navega automáticamente a la pantalla de login
 * - Diseño con fondo negro y texto blanco para máximo contraste
 */
@Composable
fun SplashScreen(navController: NavHostController) { // Almacena toda la logica del Splash

    // Efecto de corrutina para manejar el tiempo de espera
    LaunchedEffect(key1 = true) {
        // Simulamos una carga de datos (en una app real, aquí cargaríamos datos del servidor)

        delay(5000) // Espera 5 segundos

        /*
        Eliminamos la pantalla de splash del stack de navegación
        ya que es una pantalla que queremos que se muestre una vez
        cuando se inicia la aplicacion por primera vez
        */
        navController.popBackStack()

        // Indicar a que pantalla quiero navegar desde el SplashScreen
        navController.navigate(AppScreens.LoginScreen.route)
    }

    Splash()
}

/**
 * Componente que define la interfaz visual del Splash Screen.
 *
 * Características:
 * - Fondo casi negro completo (#FF242424)
 * - Logo centrado
 * - Texto de bienvenida en blanco
 * - Diseño responsive que ocupa toda la pantalla
 */
@Composable
fun Splash() { // Representa La vista del Splash

    // Estructura:
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa el maximo del contenedor
            .background(Color(0xFF242424)),
        horizontalAlignment = Alignment.CenterHorizontally, // Alinea el Screen Horizontalmente
        verticalArrangement = Arrangement.Center // Alinea el Screen Verticalmente
    ) {
        // Componente de imagen del logo
        Image(
            painter = painterResource(id = R.drawable.localhandslogo),
            contentDescription = "Logo principal de la aplicación",  // Texto para accesibilidad
            modifier = Modifier.size(200.dp)  // Tamaño fijo para el logo
        )
//        // Texto de Bienvenida
//        Text(
//            "¡Binvenidos!",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            textAlign = TextAlign.Center,
//            color = Color.White  // Texto blanco para contraste sobre fondo negro
//        )

    }
}

/**
 * Previsualización del diseño en Android Studio.
 *
 * Notas:
 * - Solo funciona en el entorno de diseño
 * - Muestra cómo se vería el componente sin ejecutar la app
 * - Útil para desarrollo iterativo
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}
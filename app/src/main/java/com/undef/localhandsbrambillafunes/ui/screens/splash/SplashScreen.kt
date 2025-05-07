package com.undef.localhandsbrambillafunes.ui.screens.splash

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.undef.localhandsbrambillafunes.R // ✅ Importa el R de tu proyecto

@Composable
fun SplashScreen() { // Almacena toda la logica del Splash
    Splash()
}

@Composable
fun Splash() { // Representa La vista del Splash

    // Estructura:
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa el maximo del contenedor
        horizontalAlignment = Alignment.CenterHorizontally, // Alinea el Screen Horizontalmente
        verticalArrangement = Arrangement.Center // Alinea el Screen Verticalmente
    ) {
        Image(
            painter = painterResource(id = R.drawable.localhands), // Referencia al drawable
            contentDescription = "Logo Local Hands",
            Modifier.size(200.dp, 200.dp)
        )
        Text(
            "Bienvenid@s!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}
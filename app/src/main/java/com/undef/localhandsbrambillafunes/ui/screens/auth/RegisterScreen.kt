package com.undef.localhandsbrambillafunes.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens

@Composable
fun RegisterScreen() {
    Register()
}

@Composable
fun Register() {
    Column {
        Text(
            text = "Bienvenido a la pantalla de Registro",
            color = Color.Red
        )
    }
}
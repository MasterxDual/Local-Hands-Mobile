package com.undef.localhandsbrambillafunes.ui.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// Componente para mostrar loading
@Composable
fun LoadingDialog(
    isLoading: Boolean,
    message: String = "Cargando..."
) {
    if (isLoading) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { },
            title = { Text("Procesando") },
            text = { Text(message) },
            confirmButton = { },
            dismissButton = { }
        )
    }
}
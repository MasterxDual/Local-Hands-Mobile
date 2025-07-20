package com.undef.localhandsbrambillafunes.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// Componente para mostrar errores
@Composable
fun ErrorDialog(
    errorMessage: String?,
    onDismiss: () -> Unit
) {
    if (errorMessage != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}
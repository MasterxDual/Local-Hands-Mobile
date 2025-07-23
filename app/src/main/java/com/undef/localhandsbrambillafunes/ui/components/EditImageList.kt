package com.undef.localhandsbrambillafunes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditImageList(
    images: List<String>,
    onImagesChange: (List<String>) -> Unit
) {
    Column {
        images.forEachIndexed { index, url ->
            OutlinedTextField(
                value = url,
                onValueChange = { newValue ->
                    val updated = images.toMutableList()
                    updated[index] = newValue
                    onImagesChange(updated)
                },
                label = { Text("Imagen ${index + 1}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        Row {
            Button(onClick = {
                onImagesChange(images + "") // Agrega un campo vacío
            }) {
                Text("Agregar imagen")
            }

            Spacer(Modifier.width(8.dp))

            if (images.isNotEmpty()) {
                Button(onClick = {
                    onImagesChange(images.dropLast(1)) // Elimina el último
                }) {
                    Text("Quitar última")
                }
            }
        }
    }
}

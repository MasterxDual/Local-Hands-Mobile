package com.undef.localhandsbrambillafunes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

// Componente reutilizable para campos de contraseña
@Composable
fun PasswordField(
    label: String,
    value: String,
    visible: Boolean,
    isValid: Boolean,
    onChange: (String) -> Unit,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (isValid) Color.Green else Color.Red,
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        ),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) { // Usar el callback
                Icon(
                    imageVector = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Ver/Ocultar contraseña"
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
    onChange: (String) -> Unit,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    val doPasswordsMatch = repeatPassword == originalPassword && repeatPassword.isNotBlank()

    OutlinedTextField(
        value = repeatPassword,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = if (doPasswordsMatch) Color.Green else Color.Red,
            focusedIndicatorColor = if (doPasswordsMatch) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (doPasswordsMatch) Color.Green.copy(alpha = 0.6f) else Color.Red.copy(alpha = 0.6f)
        ),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) { // Usar el callback
                Icon(
                    imageVector = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Ver/Ocultar contraseña"
                )
            }
        }
    )
}
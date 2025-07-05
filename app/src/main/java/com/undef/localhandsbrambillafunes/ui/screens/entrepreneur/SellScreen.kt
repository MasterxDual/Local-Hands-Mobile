package com.undef.localhandsbrambillafunes.ui.screens.entrepreneur

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.ui.viewmodel.ProductViewModel

@Composable
fun SellScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    onEditProduct: (Product) -> Unit
) {
    val products by viewModel.products.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis productos en venta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            // Abre diálogo o navega a pantalla de agregar producto
            onEditProduct(Product(
                name = "",
                description = "",
                producer = "",
                category = "",
                images = "",
                price = 0.0,
                location = ""
            ))
        }) {
            Text("Agregar nuevo producto")
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    onClick = { onEditProduct(product) }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name, style = MaterialTheme.typography.titleMedium)
                            Text(product.description, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
                            Text("Precio: $${product.price}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { viewModel.deleteProduct(product) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
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
import com.undef.localhandsbrambillafunes.data.model.viewmodel.ProductViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens


/**
 * Pantalla de gestión de productos en venta por parte del usuario.
 *
 * Esta función composable permite visualizar todos los productos creados por el usuario, agregar
 * nuevos productos, editar los existentes o eliminarlos.
 *
 * Utiliza el [ProductViewModel] para obtener y manipular la lista de productos en tiempo real.
 * La navegación a la pantalla de edición se realiza mediante el `NavController`.
 *
 * ## Funcionalidades:
 * - Visualización de productos existentes.
 * - Botón para agregar un nuevo producto (navega con ID = 0).
 * - Acceso directo a la edición de productos tocando una tarjeta.
 * - Eliminación rápida de productos mediante un botón en cada ítem.
 *
 * @param navController Controlador de navegación utilizado para mover entre pantallas.
 */
@Composable
fun SellScreen(
    navController: NavController
) {
    val viewModel: ProductViewModel = viewModel()
    val products by viewModel.products.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título de la pantalla
        Text("Mis productos en venta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        // Botón para agregar un nuevo producto
        Button(onClick = { navController.navigate(AppScreens.EditProductScreen.createRoute(0)) }) {
            Text("Agregar nuevo producto")
        }
        Spacer(Modifier.height(16.dp))
        // Lista de productos
        LazyColumn {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    // Navega a la pantalla de edición con el ID del producto
                    onClick = { navController.navigate(AppScreens.EditProductScreen.createRoute(product.id)) }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name, style = MaterialTheme.typography.titleMedium)
                            Text(product.description, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
                            Text("Precio: $${product.price}", style = MaterialTheme.typography.bodySmall)
                        }
                        // Botón para eliminar el producto
                        IconButton(onClick = { viewModel.deleteProduct(product) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
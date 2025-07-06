package com.undef.localhandsbrambillafunes.ui.screens.entrepreneur

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.ui.common.components.EditImageList
import com.undef.localhandsbrambillafunes.data.model.viewmodel.ProductViewModel

/**
 * Pantalla de edición o creación de productos en la aplicación.
 *
 * Esta función composable permite modificar un producto existente o crear uno nuevo, dependiendo
 * de si se encuentra un producto con el `productId` proporcionado.
 *
 * Se utiliza el patrón MVVM, obteniendo los productos desde el [ProductViewModel] y manipulándolos
 * mediante las funciones `addProduct`, `updateProduct` y `deleteProduct`.
 *
 * ## Características:
 * - Carga automáticamente los datos del producto si el `productId` existe.
 * - Presenta campos de entrada editables para cada atributo del producto.
 * - Permite crear, actualizar o eliminar un producto desde la UI.
 * - Navega de regreso a la pantalla anterior tras finalizar cualquier acción.
 *
 * @param navController Controlador de navegación utilizado para volver a la pantalla anterior.
 * @param productId ID del producto a editar. Si no existe, se asume que se está creando un nuevo producto.
 */
@Composable
fun EditProductScreen(
    navController: NavController,
    productId: Int
) {
    // ViewModel que contiene el estado de la lista de productos
    val viewModel: ProductViewModel = viewModel()
    val allProducts by viewModel.products.collectAsState()
    val originalProduct = allProducts.find { it.id == productId }

    // Variables de estado que contienen los campos editables
    // Si es un producto nuevo, los campos están vacíos
    var name by remember { mutableStateOf(originalProduct?.name ?: "") }
    var description by remember { mutableStateOf(originalProduct?.description ?: "") }
    var producer by remember { mutableStateOf(originalProduct?.producer ?: "") }
    var category by remember { mutableStateOf(originalProduct?.category ?: "") }
    var images by remember { mutableStateOf(originalProduct?.images ?: emptyList()) }
    var price by remember { mutableStateOf(originalProduct?.price?.toString() ?: "") }
    var location by remember { mutableStateOf(originalProduct?.location ?: "") }

    val isEditing = originalProduct != null

    // Estructura de la pantalla con inputs y acciones
    Column(Modifier.padding(16.dp)) {
        Text(if (isEditing) "Editar producto" else "Nuevo producto", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Campos de entrada para cada atributo del producto
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = producer, onValueChange = { producer = it }, label = { Text("Productor") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") })

        // Campo personalizado para la lista de imágenes
        EditImageList(images = images, onImagesChange = { images = it })

        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Ubicación") })
        Spacer(Modifier.height(16.dp))

        Row {
            // Botón para guardar o crear producto
            Button(
                onClick = {
                    val entity = Product(
                        id = originalProduct?.id ?: 0,
                        name = name,
                        description = description,
                        producer = producer,
                        category = category,
                        images = images,
                        price = price.toDoubleOrNull() ?: 0.0,
                        location = location
                    )
                    if (isEditing) {
                        viewModel.updateProduct(entity)
                    } else {
                        viewModel.addProduct(entity)
                    }
                    navController.popBackStack() // Vuelve a la lista
                }
            ) { Text(if (isEditing) "Guardar cambios" else "Crear producto") }

            Spacer(Modifier.width(16.dp))

            // Botón para eliminar producto (solo en modo edición)
            if (isEditing) {
                Button(
                    onClick = {
                        viewModel.deleteProduct(originalProduct)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}


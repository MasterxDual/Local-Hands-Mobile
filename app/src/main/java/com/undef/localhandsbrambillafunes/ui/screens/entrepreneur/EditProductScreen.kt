package com.undef.localhandsbrambillafunes.ui.screens.entrepreneur

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.data.model.Product
import com.undef.localhandsbrambillafunes.ui.common.components.EditImageList
import com.undef.localhandsbrambillafunes.ui.viewmodel.ProductViewModel

@Composable
fun EditProductScreen(
    navController: NavController,
    productId: Int,
    viewModel: ProductViewModel
) {
    val allProducts by viewModel.products.collectAsState()
    val originalProduct = allProducts.find { it.id == productId }

    // Si es un producto nuevo, los campos están vacíos
    var name by remember { mutableStateOf(originalProduct?.name ?: "") }
    var description by remember { mutableStateOf(originalProduct?.description ?: "") }
    var producer by remember { mutableStateOf(originalProduct?.producer ?: "") }
    var category by remember { mutableStateOf(originalProduct?.category ?: "") }
    var images by remember { mutableStateOf(originalProduct?.images ?: emptyList()) }
    var price by remember { mutableStateOf(originalProduct?.price?.toString() ?: "") }
    var location by remember { mutableStateOf(originalProduct?.location ?: "") }

    val isEditing = originalProduct != null

    Column(Modifier.padding(16.dp)) {
        Text(if (isEditing) "Editar producto" else "Nuevo producto", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = producer, onValueChange = { producer = it }, label = { Text("Productor") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") })
        EditImageList(images = images, onImagesChange = { images = it })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Ubicación") })
        Spacer(Modifier.height(16.dp))

        Row {
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


package com.undef.localhandsbrambillafunes.ui.screens.entrepreneur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.ui.viewmodel.products.ProductViewModel
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.undef.localhandsbrambillafunes.ui.viewmodel.session.SessionViewModel


/**
 * Pantalla de gestión de productos del usuario con diseño unificado al estilo general de la aplicación.
 *
 * Muestra la lista de productos que el usuario ha creado para la venta, con imagen, nombre y una
 * etiqueta "Ver detalle", imitando el diseño utilizado en la pantalla principal.
 *
 * La interacción está limitada únicamente al hacer clic en toda la tarjeta del producto,
 * lo cual lleva a la pantalla de edición o eliminación correspondiente.
 *
 * @param navController Controlador de navegación utilizado para moverse entre pantallas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel,
    productViewModel: ProductViewModel
) {
    val userId = sessionViewModel.getUserId()

    // Obtiene los productos del vendedor actual
    val products by productViewModel.getMyProducts(userId).collectAsState(initial = emptyList())

    Scaffold(
        // Barra superior con acciones
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Volver Atras")
                        }
                        Spacer(Modifier.width(8.dp))
                        Text("Mis productos en venta")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242424),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(AppScreens.SearchBarScreen.route) }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { navController.navigate(AppScreens.ProfileScreen.route) }) {
                        Icon(Icons.Filled.Person, contentDescription = "Perfil")
                    }
                    IconButton(onClick = { navController.navigate(AppScreens.SettingsScreen.route) }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },

        // Barra inferior
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF242424),
                contentColor = Color.White
            ) {
                val navBarItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    colors = navBarItemColors,
                    selected = false,
                    onClick = { navController.navigate(AppScreens.HomeScreen.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    colors = navBarItemColors,
                    selected = false,
                    onClick = { navController.navigate(AppScreens.FavoritesScreen.route) }
                )
            }
        }
    ) { paddingValues ->
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Button(onClick = { navController.navigate(AppScreens.EditProductScreen.createRoute(0)) }) {
                Text("Agregar nuevo producto")
            }

            Spacer(Modifier.height(16.dp))

            // Lista de productos en venta (estilo unificado)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(products) { product ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE6E6EC))
                            .clickable {
                                navController.navigate(
                                    AppScreens.ProductOwnerDetailScreen.createRoute(product.id)
                                )
                            }
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageUrl = product.images.firstOrNull()
                            // Imagen del producto (desde URL o base de datos)
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUrl),
                                contentDescription = "Imagen del producto ${product.name}",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Información del producto
                            Column {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ver detalle",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

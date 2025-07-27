package com.undef.localhandsbrambillafunes.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.R
import com.undef.localhandsbrambillafunes.data.model.ProductListItem
import com.undef.localhandsbrambillafunes.ui.viewmodel.products.ProductViewModel
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.undef.localhandsbrambillafunes.data.entity.Seller
import com.undef.localhandsbrambillafunes.ui.viewmodel.users.UserViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

/**
 * Pantalla principal de la aplicación que muestra una interfaz completa con barra superior,
 * contenido principal y barra de navegación inferior.
 *
 * Esta pantalla implementa el patrón Material Design 3 usando Scaffold como contenedor principal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,
               productViewModel: ProductViewModel = hiltViewModel<ProductViewModel>(),
               userViewModel: UserViewModel = hiltViewModel<UserViewModel>()
) {
    val products by productViewModel.products.collectAsState()
    var showSellDialog by remember { mutableStateOf(false) }

    /**
     * Scaffold es el componente base que proporciona la estructura básica de la pantalla
     * con áreas para barra superior, contenido principal y barra inferior.
     */
    Scaffold(
        // Barra Superior con título y acciones
        topBar = {
            /**
             * TopAppBar proporciona la barra superior con título y acciones.
             * En este caso incluye el logo de la marca y botones de acción.
             */
            TopAppBar(
                // Logo de la Marca
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        /**
                         * Logo de la aplicación con descripción para accesibilidad
                         */
                        Image(
                            painter = painterResource(id = R.drawable.localhandslogo),
                            contentDescription = "Logo principal de la aplicación",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 8.dp),
                        )
                        /**
                         * Título de la aplicación con estilo en negrita
                         */
                        Text(
                            text = stringResource(R.string.app_name),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                // Colores personalizados para la barra superior
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242424),  // Color oscuro para fondo
                    titleContentColor = Color.White,      // Texto blanco
                    actionIconContentColor = Color.White  // Iconos blancos
                ),
                // Acciones disponibles en la barra superior
                actions = {
                    /**
                     * Botón de búsqueda que navega a la pantalla de búsqueda
                     */
                    IconButton(onClick = { navController.navigate(route = AppScreens.SearchBarScreen.route) }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }

                    /**
                     * Botón de perfil que navega a la pantalla de perfil
                     */
                    IconButton(onClick = { navController.navigate(route = AppScreens.ProfileScreen.route) }) {
                        Icon(Icons.Filled.Person, contentDescription = "Sección de Perfil")
                    }

                    /**
                     * Botón de configuración que navega a la pantalla de ajustes
                     */
                    IconButton(onClick = { navController.navigate(route = AppScreens.SettingsScreen.route) }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Sección de Settings")
                    }
                }
            )
        },

        // Barra inferior de navegación
        bottomBar = {
            /**
             * NavigationBar proporciona la barra de navegación inferior con iconos y etiquetas
             */
            NavigationBar(
                containerColor = Color(0xFF242424),  // Fondo oscuro
                contentColor = Color.White           // Texto blanco
            ) {
                /**
                 * Configuración de colores para los elementos de navegación
                 */
                val navBarItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,      // Ícono seleccionado
                    unselectedIconColor = Color.White,     // Ícono no seleccionado
                    selectedTextColor = Color.White,      // Texto seleccionado
                    unselectedTextColor = Color.White,    // Texto no seleccionado
                    indicatorColor = Color.Transparent     // Sin indicador visual
                )

                /**
                 * Elementos de navegación disponibles:
                 * - Inicio (actualmente seleccionado)
                 * - Favoritos
                 * - Vender
                 * - Categorías
                 */
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { /* Implementar navegación a Home */ }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { navController.navigate(route = AppScreens.FavoritesScreen.route) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Shop, contentDescription = "Vender") },
                    label = { Text("Vender")},
                    colors = navBarItemColors,
                    selected = false,
                    onClick = { showSellDialog = true }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Menu, contentDescription = "Categorias") },
                    label = { Text("Categorías")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = {  }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Encabezado de sección
            item {
                Text(
                    text = "Productos Destacados",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Lista de productos
            items(
                items = products,
                key = { it.id } // Clave única para cada producto
            ) { product ->
                ProductListItem(product = product, navController = navController)
            }
        }
    }

    if (showSellDialog) {
        HandleNavigationToSellScreen(
            navController = navController,
            userViewModel = userViewModel,
            onDismiss = { showSellDialog = false }
        )
    }


}

@Composable
fun HandleNavigationToSellScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showConfirmationDialog by remember { mutableStateOf(true) }
    var showEntrepreneurDialog by remember { mutableStateOf(false) }
    var entrepreneurshipName by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // 🔹 Paso 1: Confirmación
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                showConfirmationDialog = false
                onDismiss()
            },
            title = { Text("Convertirse en emprendedor") },
            text = { Text("Usted está a punto de convertirse en emprendedor. ¿Está seguro?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmationDialog = false
                    showEntrepreneurDialog = true
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmationDialog = false
                    onDismiss()
                }) {
                    Text("No")
                }
            }
        )
    }

    // 🔹 Paso 2: Ingreso de nombre del emprendimiento
    if (showEntrepreneurDialog) {
        AlertDialog(
            onDismissRequest = {
                showEntrepreneurDialog = false
                onDismiss()
            },
            title = { Text("Nombre del emprendimiento") },
            text = {
                Column {
                    Text("Ingrese el nombre de su emprendimiento:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = entrepreneurshipName,
                        onValueChange = { entrepreneurshipName = it },
                        placeholder = { Text("Ej: Dulzuras del Valle") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        val user = userViewModel.getUserById()
                        val seller = Seller(
                            id = user.id,
                            name = user.name,
                            lastname = user.lastName,
                            email = user.email,
                            phone = user.phone,
                            address = user.address,
                            entrepreneurship = entrepreneurshipName
                        )
                        userViewModel.createSeller(seller)
                        navController.navigate(AppScreens.CategoryScreen.route)
                        onDismiss()
                    }
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEntrepreneurDialog = false
                    onDismiss()
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}



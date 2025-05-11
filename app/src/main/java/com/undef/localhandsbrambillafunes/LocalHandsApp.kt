package com.undef.localhandsbrambillafunes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalHandsApp(navController: NavController) {
    Scaffold(
        // Barra Superior con título y acciones
        topBar = {
            TopAppBar(
                // Logo de la Marca
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically, // Centrar el logo con el texto verticalmente
                        modifier = Modifier.clickable() { /* TODO: Implementar navegacion */ }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.localhandslogo), // Logo de la app
                            contentDescription = "Logo principal de la aplicación",  // Texto para accesibilidad
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 8.dp),

                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                // Colores para la barra superior
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF242424),  // Color de fondo
                    titleContentColor = Color.White,      // Color del texto
                    actionIconContentColor = Color.White  // Color de los iconos de acción
                ),
                actions = {
                    // Botón para ir a Favoritos
                    IconButton(onClick = { /* TODO: Implementar navegación */ }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Seccion de Favoritos"
                        )
                    }

                    // Botón para ir a Perfil
                    IconButton(onClick = { /* TODO: Implementar navegación */ }) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Seccion de Perfil"
                        )
                    }

                    // Botón para ir a Configuración
                    IconButton(onClick = { navController.navigate(route = AppScreens.SettingsScreen.route) }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Seccion de Settings"
                        )
                    }
                }
            )
        },


        // Implementacion para Material3:
        // Barra inferior con navegacion principal
        bottomBar = {
            // Navegacion inferior con iconos
            NavigationBar(
                containerColor = Color(0xFF242424),
                contentColor = Color.White
            ) {

                // Esquema de color para los diferentes estados de los botones
                val navBarItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,      // Ícono seleccionado
                    unselectedIconColor = Color.White,     // Ícono no seleccionado
                    selectedTextColor = Color.White,      // Texto seleccionado
                    unselectedTextColor = Color.White,      // Texto no seleccionado
                    indicatorColor = Color.Transparent     // Quitar el recuadro
                )

                // Boton de Home o inicio (actual)
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { /* Implementar navegar a Home aunque estamos en Home */ }
                )
                // Boton de explorar o buscar
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                    label = { Text("Buscar")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { /* Implementar Busqueda */ }
                )
                // Boton para vender
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Shop, contentDescription = "Vender") },
                    label = { Text("Vender")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { /* TODO: Implementar navegacion */ }
                )
                // Boton de Categorias
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Menu, contentDescription = "Categorias") },
                    label = { Text("Categorias")},
                    colors = navBarItemColors,
                    selected = true,
                    onClick = { /* TODO: Implementar navegacion */ }
                )
            }

        }

        // Implementacion para Material2 (Descartada):
//        // Barra inferior con navegación principal
//        bottomBar = {
//            BottomAppBar(
//                // Navegación inferior con íconos
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    IconButton(onClick = { /* TODO: Implementar navegacion */ }) { }
//                }
//            )
//        }
    ) { paddingValues ->
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Sección de productos destacados
            Text(
                text = "Productos Destacados",
                modifier = Modifier.padding(8.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )

            // Lista de productos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // Lista estática de productos para la interfaz
            }
        }
    }
}
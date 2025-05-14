package com.undef.localhandsbrambillafunes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.undef.localhandsbrambillafunes.ui.screens.home.LocalHandsApp
import com.undef.localhandsbrambillafunes.data.model.ProductProvider
import com.undef.localhandsbrambillafunes.ui.screens.auth.ForgotPasswordScreen
import com.undef.localhandsbrambillafunes.ui.screens.auth.LoginScreen
import com.undef.localhandsbrambillafunes.ui.screens.auth.RegisterScreen
import com.undef.localhandsbrambillafunes.ui.screens.favorites.FavoritesScreen
import com.undef.localhandsbrambillafunes.ui.screens.home.components.SearchBarScreen
import com.undef.localhandsbrambillafunes.ui.screens.profile.ProfileScreen
import com.undef.localhandsbrambillafunes.ui.screens.productdetail.ProductDetailScreen
import com.undef.localhandsbrambillafunes.ui.screens.settings.SettingsScreen
import com.undef.localhandsbrambillafunes.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {

    // Crear NavController por defecto
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route // Primer destino de la navegacion, en este caso, la SplashScreen
    ) {
        // Aca van las capacidades de navegacion, es decir, las pantllas por donde navegara el NavHost

        composable(AppScreens.SplashScreen.route) {
            // composable que representa la SplashScreen
            SplashScreen(navController)
        }
        composable(AppScreens.LoginScreen.route) {
            // composable que representa la LoginScreen
            LoginScreen(navController)
        }
        composable(AppScreens.ForgotPasswordScreen.route) {
            // composable que representa la ForgotPasswordScreen
            ForgotPasswordScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            // composable que representa la RegisterScreen
            RegisterScreen(navController)
        }
        composable(AppScreens.LocalHandsApp.route) {
            // composable que representa la LocalHandsApp
            LocalHandsApp(navController)
        }
        composable(AppScreens.SettingsScreen.route) {
            // composable que representa la SettingsScreen
            SettingsScreen()
        }
        composable(AppScreens.ProfileScreen.route) {
            // composable que representa la ProfileScreen
            ProfileScreen(navController)
        }
        composable(
            // Define una pantalla que corresponde a la ruta ProducDetailsScreen
            route = AppScreens.ProductDetailScreen.route,
            // Espera un argumento llamado '´productId' de tipo entero
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->  // Recibimos NavBackStackEntry que contiene los argumentos de navegacion
            // Extraemos el argumento productId como Int, si no existe, sale del composable con 'return@composable'
            val productId = navBackStackEntry.arguments?.getInt("productId") ?: return@composable

            // Buscamos el producto en la lista, si no encuentra nada sale del composable
            val product = ProductProvider.products.find { it.id == productId } ?: return@composable

            // composable que representa la pantalla de ProductDetailScreen
            ProductDetailScreen(
                navController = navController,
                product = product
            )
        }

        composable(AppScreens.FavoritesScreen.route) {
            FavoritesScreen(navController)
        }

        composable(AppScreens.SearchBarScreen.route) {
            SearchBarScreen(navController)
        }
    }
}
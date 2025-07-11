package com.undef.localhandsbrambillafunes.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.undef.localhandsbrambillafunes.data.local.db.ApplicationDatabase
import com.undef.localhandsbrambillafunes.data.local.viewmodel.ProductViewModel
import com.undef.localhandsbrambillafunes.data.local.viewmodel.SessionViewModel
import com.undef.localhandsbrambillafunes.data.local.viewmodel.SessionViewModelFactory
import com.undef.localhandsbrambillafunes.data.local.repository.UserRepository
import com.undef.localhandsbrambillafunes.data.local.viewmodel.FavoriteViewModel
import com.undef.localhandsbrambillafunes.ui.screens.auth.ForgotPasswordScreen
import com.undef.localhandsbrambillafunes.ui.screens.auth.LoginScreen
import com.undef.localhandsbrambillafunes.ui.screens.auth.RegisterScreen
import com.undef.localhandsbrambillafunes.ui.screens.favorites.FavoritesScreen
import com.undef.localhandsbrambillafunes.ui.screens.home.components.SearchBarScreen
import com.undef.localhandsbrambillafunes.ui.screens.home.HomeScreen
import com.undef.localhandsbrambillafunes.ui.screens.home.components.CategoryScreen
import com.undef.localhandsbrambillafunes.ui.screens.profile.ProfileScreen
import com.undef.localhandsbrambillafunes.ui.screens.productdetail.ProductDetailScreen
import com.undef.localhandsbrambillafunes.ui.screens.settings.SettingsScreen
import com.undef.localhandsbrambillafunes.ui.screens.splash.SplashScreen
import com.undef.localhandsbrambillafunes.ui.screens.entrepreneur.SellScreen
import com.undef.localhandsbrambillafunes.ui.screens.entrepreneur.EditProductScreen
import com.undef.localhandsbrambillafunes.ui.screens.entrepreneur.ProductOwnerDetailScreen


/**
 * Composable principal que configura y gestiona la navegación entre pantallas de la aplicación.
 * Define el NavHost con todas las rutas disponibles y sus respectivos composables.
 */
@Composable
fun Navigation() {
    // Crear NavController que recordará el estado de navegación
    val navController = rememberNavController()

    val context = LocalContext.current
    val userRepository = remember {
        UserRepository(
            ApplicationDatabase.getInstance(context.applicationContext as Application).userDao()
        )
    }
    val sessionViewModel: SessionViewModel = viewModel(
        factory = SessionViewModelFactory(LocalContext.current.applicationContext as Application, userRepository)
    )

    // Instancia compartida de ProductViewModel en el scope de Navigation
    // Esto se hace con el fin de no tener que instanciar varias veces al productViewModel y así
    // tengamos los mismos productos en todas las pantallas que lo utilicemos
    val productViewModel: ProductViewModel = viewModel()

    val favoriteViewModel: FavoriteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route // Primer destino de la navegación será la pantalla de splash
    ) {
        // Definición de todas las rutas de navegación y sus composables asociados

        /**
         * Pantalla de splash (inicial) que se muestra al arrancar la aplicación.
         */
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }

        /**
         * Pantalla de inicio de sesión donde los usuarios pueden autenticarse.
         */
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController, sessionViewModel)
        }

        /**
         * Pantalla para la recuperación de contraseña olvidada.
         */
        composable(AppScreens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController)
        }

        /**
         * Pantalla de registro para nuevos usuarios.
         */
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController, sessionViewModel)
        }

        /**
         * Pantalla principal de la aplicación que se muestra después de la autenticación.
         */
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController, productViewModel)
        }

        /**
         * Pantalla de configuración de la aplicación.
         */
        composable(AppScreens.SettingsScreen.route) {
            SettingsScreen(navController)
        }

        /**
         * Pantalla de perfil del usuario donde se muestra y puede editar su información.
         */
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }

        /**
         * Pantalla de detalle de producto que recibe un ID como parámetro.
         * Define un argumento 'productId' de tipo entero que se extrae de la URL.
         */
        composable(
            route = AppScreens.ProductDetailScreen.route,
            // Espera un argumento llamado '´productId' de tipo entero
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType // Define el tipo del argumento como entero
                }
            )
        ) { navBackStackEntry ->  // Recibe la entrada de navegación
            // Extrae el argumento productId del backstack, si no existe retorna sin hacer nada
            val productId = navBackStackEntry.arguments?.getInt("productId") ?: return@composable

            // Busca el producto correspondiente en la base de datos
            val product = productViewModel.products.collectAsState().value.find { it.id == productId }

            // Muestra la pantalla de detalle con el producto encontrado
            product?.let {
                ProductDetailScreen(
                    navController = navController,
                    product = it,
                    sessionViewModel
                )
            }
        }

        /**
         * Pantalla que muestra los productos marcados como favoritos por el usuario.
         */
        composable(AppScreens.FavoritesScreen.route) {
            FavoritesScreen(navController, sessionViewModel, favoriteViewModel)
        }

        /**
         * Pantalla de búsqueda que permite al usuario buscar productos.
         */
        composable(AppScreens.SearchBarScreen.route) {
            SearchBarScreen(navController)
        }

        /**
         * Pantalla que muestra las categorías de productos disponibles.
         */
        composable(AppScreens.CategoryScreen.route) {
            CategoryScreen(navController)
        }

        /**
         * Pantalla para vender productos.
         */
        composable(AppScreens.SellScreen.route) {
            SellScreen(
                navController,
                sessionViewModel,
                productViewModel
            )
        }

        /**
         * Pantalla para modificar productos
         * */
        composable(
            route = AppScreens.EditProductScreen.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            EditProductScreen(
                navController = navController,
                productId = productId,
                productViewModel,
                sessionViewModel
            )
        }

        /**
         * Pantalla para ver los detalles de un producto en venta
         * */
        composable(
            route = AppScreens.ProductOwnerDetailScreen.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val productViewModel = productViewModel
            val product = productViewModel.products.collectAsState().value.find { it.id == productId }
            product?.let {
                ProductOwnerDetailScreen(
                    navController = navController,
                    product = it,
                    onEdit = { navController.navigate(AppScreens.EditProductScreen.createRoute(it.id)) },
                    onDelete = {
                        productViewModel.deleteProduct(it)
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}
package com.undef.localhandsbrambillafunes.ui.navigation

/**
 * Clase sellada que define las diferentes pantallas de la aplicación.
 * Cada pantalla se representa como un objeto que hereda de esta clase.
 *
 * @param route String que define la ruta de navegación para la pantalla.
*/
sealed class AppScreens(val route: String) {

    // Objetos que representan las pantallas de la aplicación

    // Pantalla de inicio/splash que se muestra al arrancar la aplicación.
    object SplashScreen: AppScreens("splash_screen")

    // Pantalla de inicio de sesión.
    object LoginScreen: AppScreens("login_screen")

    // Pantalla para la recuperación de contraseña olvidada.
    object ForgotPasswordScreen: AppScreens("forgot_password_screen")

    // Pantalla de registro de nuevos usuarios.
    object RegisterScreen: AppScreens("register_screen")

    // Pantalla principal de la aplicación.
    object HomeScreen: AppScreens("home_screen")

    // Pantalla de configuración de la aplicación.
    object SettingsScreen: AppScreens("settings_screen")

    // Pantalla de perfil del usuario.
    object ProfileScreen: AppScreens("profile_screen")

    // Pantalla de detalle de producto que recibe un ID de producto como parámetro.
    object ProductDetailScreen : AppScreens("product_detail_screen/{productId}") {

        /**
         * Función auxiliar para construir la ruta con el ID del producto.
         *
         * @param productId ID del producto a mostrar.
         * @return String con la ruta completa incluyendo el ID del producto.
         */
        fun createRoute(productId: Int) = "product_detail_screen/$productId"
    }

    // Pantalla de categorías de productos.
    object CategoryScreen: AppScreens("category_screen")

    // Pantalla de productos favoritos del usuario.
    object FavoritesScreen: AppScreens("favorite_screens")

    // Pantalla de búsqueda de productos.
    object SearchBarScreen: AppScreens("search_bar_screen")

    // Pantalla para emprender productos.
    object SellScreen : AppScreens("sell_screen")
}
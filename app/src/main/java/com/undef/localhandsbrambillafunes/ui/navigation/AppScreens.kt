package com.undef.localhandsbrambillafunes.ui.navigation

import com.undef.localhandsbrambillafunes.data.model.Product

sealed class AppScreens(val route: String, val product: Product? = null) {

    // Objetos que representan las Pantallas de la app
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")
    object ForgotPasswordScreen: AppScreens("forgot_password_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LocalHandsApp: AppScreens("local_hands_app")
    object SettingsScreen: AppScreens("settings_screen")
<<<<<<< HEAD
    object ProductDetailScreen: AppScreens("product_detail_screen/{productId}") {
=======
    object ProfileScreen: AppScreens("profile_screen")
    object ProductDetailScreen : AppScreens("product_detail_screen/{productId}") {
>>>>>>> 96a749aefdaebf18a977d8e9b7435c7ac2bb0269
        // Función auxiliar para construir la ruta con argumentos
        fun createRoute(productId: Int) = "product_detail_screen/$productId"
    }
    object FavoritesScreen: AppScreens("favorite_screens")
}
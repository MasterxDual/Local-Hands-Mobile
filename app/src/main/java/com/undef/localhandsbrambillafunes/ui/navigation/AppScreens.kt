package com.undef.localhandsbrambillafunes.ui.navigation

sealed class AppScreens(val route: String) {

    // Objetos que representan las Pantallas de la app
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")
}
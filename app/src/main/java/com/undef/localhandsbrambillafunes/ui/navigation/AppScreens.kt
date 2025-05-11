package com.undef.localhandsbrambillafunes.ui.navigation

sealed class AppScreens(val route: String) {

    // Objetos que representan las Pantallas de la app
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")
    object ForgotPasswordScreen: AppScreens("forgot_password_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LocalHandsApp: AppScreens("local_hands_app")
    object SettingsScreen: AppScreens("settings_screen")
}
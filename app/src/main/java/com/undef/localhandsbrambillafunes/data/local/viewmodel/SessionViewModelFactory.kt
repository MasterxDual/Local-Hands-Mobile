package com.undef.localhandsbrambillafunes.data.local.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.undef.localhandsbrambillafunes.data.local.repository.UserRepository

//Si queremos que el userId persista aunque se cierre la app, le podemos agregar SharedPreferences al ViewModel

/**
 * Fábrica personalizada para crear instancias de [SessionViewModel].
 *
 * Esta clase implementa [ViewModelProvider.Factory] y permite inyectar dependencias necesarias
 * (en este caso, una instancia de [Application]) al construir el ViewModel.
 *
 * Es especialmente útil cuando un ViewModel requiere parámetros en su constructor,
 * lo cual no es posible manejar directamente con el proveedor predeterminado de ViewModels.
 *
 * @param application instancia de [Application] que se pasa al ViewModel.
 */
class SessionViewModelFactory(private val application: Application,
                              private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    /**
     * Crea una nueva instancia del ViewModel dado el tipo de clase proporcionado.
     *
     * @param modelClass clase del ViewModel que se desea instanciar.
     * @return una instancia de [SessionViewModel] si el tipo coincide.
     * @throws IllegalArgumentException si la clase solicitada no es [SessionViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(application, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.undef.localhandsbrambillafunes.data.local.db

import android.app.Application
import androidx.room.Room

/**
 * Clase personalizada que extiende [Application] y se utiliza para inicializar recursos globales
 * al iniciar la aplicación, en este caso, la base de datos local `ApplicationDatabase` mediante Room.
 *
 * Esta clase es registrada en el `AndroidManifest.xml` para que el sistema la utilice como punto
 * de entrada de la aplicación.
 *
 * ## Funcionalidad:
 * - Inicializa la instancia única de la base de datos `ApplicationDatabase` al iniciar la app.
 * - Proporciona acceso global a la base de datos mediante un `companion object`, lo cual permite
 *   su uso desde cualquier parte de la aplicación.
 *
 * ### Nota:
 * - La base de datos se construye con `Room.databaseBuilder(...)`.
 * - Esta implementación no incluye migraciones explícitas; para producción, se recomienda gestionarlas adecuadamente.
 */
class ProductApplication: Application() {
    companion object {
        /**
         * Instancia global y reutilizable de la base de datos de productos.
         * Debe accederse con precaución para evitar pérdidas de contexto.
         */
        lateinit var database: ApplicationDatabase
    }

    /**
     * Método llamado cuando la aplicación se crea por primera vez.
     * Aquí se inicializa la base de datos local utilizando Room.
     */
    override fun onCreate() {
         super.onCreate()

        // Construcción e inicialización de la base de datos de la aplicacion
        database = Room.databaseBuilder(this, ApplicationDatabase::class.java, "ApplicationDatabase").build()
    }
}
package com.undef.localhandsbrambillafunes

import android.app.Application
import androidx.room.Room
import com.undef.localhandsbrambillafunes.data.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocalHandsApplication : Application() {

    companion object {
        /**
         * Instancia global y reutilizable de la base de datos de productos.
         * Debe accederse con precaución para evitar pérdidas de contexto.
         */
        lateinit var database: AppDatabase
    }

    /**
     * Método llamado cuando la aplicación se crea por primera vez.
     * Aquí se inicializa la base de datos local utilizando Room.
     */
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, "AppDatabase")
//            .fallbackToDestructiveMigration(false) // Esto recreará la DB en cambios de esquema
            .build()
    }
}
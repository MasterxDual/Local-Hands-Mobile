package com.undef.localhandsbrambillafunes.data.model.db

import android.app.Application
import androidx.room.Room

//Para registrar la base de datos e inicializarla en la aplicación
class ProductApplication: Application() {
    companion object { //Para tener acceso desde cualquier parte de la aplicación
        lateinit var database: ProductDatabase
    }

    // Creamos la inicialización
    override fun onCreate() {
         super.onCreate()

        // Construimos la base de datos
        database = Room.databaseBuilder(this, ProductDatabase::class.java, "ProductDatabase").build()
    }
}
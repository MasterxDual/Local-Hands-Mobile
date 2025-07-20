package com.undef.localhandsbrambillafunes.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.undef.localhandsbrambillafunes.data.dao.ProductDao

// Importamos las Entidades
import com.undef.localhandsbrambillafunes.data.entity.User
import com.undef.localhandsbrambillafunes.data.dao.UserDao
import com.undef.localhandsbrambillafunes.data.model.Product

/**
 * Base de datos principal de la aplicación (Room Database)
 *
 * @property entities Lista de entidades incluidas (User, Product)
 * @property version Versión actual del esquema
 *
 * @method userDao Proporciona acceso al DAO de usuarios
 * @method productDao Proporciona acceso al DAO de productos
 */
@Database(
    entities = [User::class, Product::class], // Entidades
    version = 2, // Incrementar cuando se modifique el esquema
    exportSchema = false // No exportar esquema para simplificar
)
@TypeConverters(Converters::class) //Para cargar List<String> de Product
abstract class AppDatabase: RoomDatabase() {
    /**
     * Proporciona acceso al DAO de Usuarios
     * @return Instancia de [UserDao] para ejecutar operaciones CRUD sobre la BD.
     */
    abstract fun userDao(): UserDao

    /**
     * Proporciona acceso al DAO de productos.
     * @return Instancia de [ProductDao] para ejecutar operaciones CRUD sobre la BD.
     */
    abstract fun productDao(): ProductDao

    companion object {

        /**
         * Instancia singleton de la base de datos, compartida a lo largo de toda la aplicación.
         * Marcada como `@Volatile` para asegurar la visibilidad entre hilos.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Obtiene instancia de la base de datos (Singleton)
         *
         * @param context Contexto de la aplicación
         * @return Instancia de AppDatabase
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Construye la base de datos si no existe
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Nombre del archivo de base de datos
                )
                    .fallbackToDestructiveMigration(true) // Permitir migraciones destructivas
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Obtiene instancia existente o crea nueva
         *
         * @param context Contexto de la aplicación
         * @return Instancia única de AppDatabase
         */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { INSTANCE = it }
            }
    }
}
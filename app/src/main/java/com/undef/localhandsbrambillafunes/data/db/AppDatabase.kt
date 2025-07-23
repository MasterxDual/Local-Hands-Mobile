package com.undef.localhandsbrambillafunes.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.undef.localhandsbrambillafunes.data.dao.FavoriteDao
import com.undef.localhandsbrambillafunes.data.dao.ProductDao

// Importamos las Entidades
import com.undef.localhandsbrambillafunes.data.entity.User
import com.undef.localhandsbrambillafunes.data.dao.UserDao
import com.undef.localhandsbrambillafunes.data.entity.Favorite
import com.undef.localhandsbrambillafunes.data.entity.Product

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
    entities = [User::class, Product::class, Favorite::class], // Entidades
    version = 5, // Incrementar cuando se modifique el esquema
    exportSchema = true // Exportar el esquema
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

    /**
     * Proporciona acceso al DAO de favoritos.
     *
     * @return Instancia de [FavoriteDao] para ejecutar operaciones CRUD sobre la base de datos.
     */
    abstract fun favoriteDao(): FavoriteDao

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
         * Devuelve la instancia existente de la base de datos o la crea si aún no ha sido inicializada.
         *
         * Utiliza el contexto de la aplicación para evitar fugas de memoria.
         * Aplica `fallbackToDestructiveMigration()` para eliminar y recrear la base de datos
         * en caso de que ocurra una incompatibilidad entre versiones de esquema.
         *
         * ⚠️ Este enfoque implica pérdida de datos ante cambios estructurales.
         *
         * @param context Contexto de la aplicación.
         * @return Instancia única de [AppDatabase].
         */
//        fun getInstance(context: Context): AppDatabase =
//            AppDatabase.Companion.INSTANCE ?: synchronized(this) {
//                AppDatabase.Companion.INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "ProductDatabase"
//                )
//                    .fallbackToDestructiveMigration(true) // Borra la base de datos vieja en caso de que se modifique la estructura de la misma y se incremente la versión
//                    .build().also { AppDatabase.Companion.INSTANCE = it }
//            }
    }
}
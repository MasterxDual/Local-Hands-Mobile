package com.undef.localhandsbrambillafunes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.undef.localhandsbrambillafunes.data.model.Product;
import com.undef.localhandsbrambillafunes.data.dao.ProductDao;

/**
 * Clase abstracta que representa la base de datos local de productos utilizando Room.
 *
 * Define las entidades, versión, y convertidores necesarios para el almacenamiento de datos.
 * También proporciona acceso al DAO [ProductDao] para realizar operaciones sobre la entidad [Product].
 *
 * Esta base de datos se configura como singleton utilizando un patrón thread-safe con `@Volatile`
 * y `synchronized`, lo cual garantiza que solo exista una instancia activa durante el ciclo de vida
 * de la aplicación.
 *
 * ## Configuración:
 * - Entidades: [Product]
 * - Versión: 1
 * - Convertidores: [Converters] (necesarios para manejar listas de imágenes `List<String>`)
 */
@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class) //Para cargar List<String> de Product
abstract class ProductDatabase: RoomDatabase() {
    /**
     * Proporciona acceso al DAO de productos.
     *
     * @return Instancia de [ProductDao] para ejecutar operaciones CRUD sobre la base de datos.
     */
    abstract fun productDao(): ProductDao

    companion object {
        /**
         * Instancia singleton de la base de datos, compartida a lo largo de toda la aplicación.
         * Marcada como `@Volatile` para asegurar la visibilidad entre hilos.
         */
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        /**
         * Devuelve la instancia existente de la base de datos o la crea si aún no ha sido inicializada.
         *
         * Utiliza el contexto de la aplicación para evitar fugas de memoria.
         *
         * @param context Contexto de la aplicación.
         * @return Instancia única de [ProductDatabase].
         */
        fun getInstance(context: Context): ProductDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "ProductDatabase"
                ).build().also { INSTANCE = it }
            }
    }
}

package com.undef.localhandsbrambillafunes.data.model.db;

import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters;

import com.undef.localhandsbrambillafunes.data.local.db.Converters;
import com.undef.localhandsbrambillafunes.data.model.Product;
import com.undef.localhandsbrambillafunes.data.model.dao.ProductDao;

// Creamos la base de datos de productos
@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class) //Para cargar List<String> de Product
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

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

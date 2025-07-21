package com.undef.localhandsbrambillafunes.di

import android.content.Context
import androidx.room.Room
import com.undef.localhandsbrambillafunes.data.dao.UserDao
import com.undef.localhandsbrambillafunes.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt que provee las dependencias relacionadas con la base de datos Room.
 *
 * Este módulo es responsable de:
 * - Crear y configurar la instancia única de la base de datos de la aplicación
 * - Proporcionar los DAOs (Data Access Objects) necesarios
 *
 * Las dependencias están configuradas para tener alcance de aplicación (Singleton).
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provee una instancia única de [AppDatabase] configurada con Room.
     *
     * @param context Contexto de aplicación inyectado por Hilt
     * @return Instancia configurada de la base de datos Room
     *
     * Configuración actual:
     * - Nombre de la base de datos: "app_database"
     * - Sin migraciones explícitas (en producción deberían manejarse)
     * - Sin callback de creación (opcional para operaciones post-creación)
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            //.addMigrations() // Agregar migraciones aquí cuando sea necesario
            //.addCallback()  // Callbacks para operaciones post-creación/popen
            .build()
    }

    /**
     * Provee el DAO para operaciones de usuario.
     *
     * @param database Instancia de la base de datos inyectada
     * @return Implementación concreta de [UserDao]
     *
     * Notas:
     * - No requiere alcance Singleton ya que Room maneja internamente
     *   las instancias del DAO de manera eficiente
     * - El DAO es generado automáticamente por Room en tiempo de compilación
     */
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
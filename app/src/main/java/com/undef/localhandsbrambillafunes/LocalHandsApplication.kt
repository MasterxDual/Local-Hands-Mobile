package com.undef.localhandsbrambillafunes

import android.app.Application
import androidx.room.Room
import com.undef.localhandsbrambillafunes.data.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocalHandsApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, "AppDatabase")
            .fallbackToDestructiveMigration(false) // Esto recreará la DB en cambios de esquema
            .build()
    }
}
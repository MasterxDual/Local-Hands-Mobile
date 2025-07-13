package com.undef.localhandsbrambillafunes.ui.screens.entrepreneur

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * Copia una URI seleccionada (por ejemplo, desde la galería o explorador de archivos)
 * hacia el almacenamiento interno de la aplicación. Genera un nombre único basado en el tiempo.
 * Es necesario para que se puedan utilizar las imagenes desde la base de datos y mostrarlas en la UI.
 * @param context El contexto de la aplicación, necesario para acceder al ContentResolver.
 * @param uri La URI original de la imagen seleccionada.
 * @return La ruta absoluta del archivo copiado como String, o `null` si hubo un error.
 */
fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

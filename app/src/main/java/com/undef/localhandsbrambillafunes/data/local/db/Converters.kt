package com.undef.localhandsbrambillafunes.data.local.db

import androidx.room.TypeConverter

/**
 * Clase de conversión utilizada por Room para manejar tipos de datos no compatibles directamente,
 * como listas de cadenas (`List<String>`), al persistir objetos en la base de datos.
 *
 * En este caso, se encarga de convertir la propiedad `images` del modelo [Product],
 * ya que Room no puede almacenar listas directamente.
 *
 * La conversión se realiza mediante serialización simple utilizando el carácter `|` como separador.
 * Este enfoque es suficiente siempre que las URLs de las imágenes no contengan dicho carácter.
 *
 * ### Ejemplo:
 * - Lista: `["url1", "url2", "url3"]`
 * - Cadena almacenada: `"url1|url2|url3"`
 */
class Converters {
    @TypeConverter
    fun fromImageList(images: List<String>): String {
        return images.joinToString("|")
    }
    /*images.joinToString("|")*/

    @TypeConverter
    fun toImageList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split("|")
    }
    /*if (data.isEmpty()) emptyList() else data.split("|")*/
}

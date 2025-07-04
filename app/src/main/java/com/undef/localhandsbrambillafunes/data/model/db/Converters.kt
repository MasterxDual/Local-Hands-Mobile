package com.undef.localhandsbrambillafunes.data.local.db

import androidx.room.TypeConverter

//Room no puede guardar listas directamente, así que utilizamos un TypeConverter para images de Product
class Converters {
    @TypeConverter
    fun fromImageList(images: List<String>): String =
        images.joinToString(",")

    @TypeConverter
    fun toImageList(data: String): List<String> =
        if (data.isEmpty()) emptyList() else data.split(",")
}

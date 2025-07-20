package com.undef.localhandsbrambillafunes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa un usuario en la base de datos local.
 *
 * Esta clase está mapeada a la tabla `UserEntity` mediante la anotación [Entity] de Room.
 * Se utiliza para almacenar información básica de usuarios, como nombre, correo electrónico
 * y credenciales de acceso.
 *
 * @property id Identificador único del usuario. Es la clave primaria y se genera automáticamente.
 * @property name Nombre del usuario.
 * @property surname Apellido del usuario.
 * @property email Dirección de correo electrónico del usuario. Se espera que sea único.
 * @property password Contraseña del usuario, almacenada en texto plano o con hash según la implementación.
 */
@Entity(tableName = "UserEntity")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, //Empieza en 1 automáticamente
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)

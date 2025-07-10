package com.undef.localhandsbrambillafunes.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.undef.localhandsbrambillafunes.data.model.entities.User

import androidx.room.*

/**
 * Interfaz DAO (Data Access Object) para acceder a los datos de la entidad [User].
 *
 * Define las operaciones CRUD que se pueden realizar sobre la tabla `UserEntity`
 * utilizando la librería Room.
 */
@Dao
interface UserDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param user Instancia del usuario a insertar.
     * @return ID generado para el nuevo usuario.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param user Usuario con los datos actualizados.
     */
    @Update
    suspend fun updateUser(user: User)

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param user Usuario a eliminar.
     */
    @Delete
    suspend fun deleteUser(user: User)

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Instancia de [User], o `null` si no se encuentra.
     */
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    /**
     * Obtiene todos los usuarios almacenados.
     *
     * @return Lista de usuarios.
     */
    @Query("SELECT * FROM UserEntity")
    suspend fun getAllUsers(): List<User>

    /**
     * Verifica si existe un usuario con un correo electrónico dado.
     *
     * @param email Correo electrónico a verificar.
     * @return Instancia de [User], o `null` si no existe.
     */
    @Query("SELECT * FROM UserEntity WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
}

package com.undef.localhandsbrambillafunes.data.dao

import androidx.room.*
import com.undef.localhandsbrambillafunes.data.entity.User


/**
 * DAO (Data Access Object) para operaciones CRUD de usuarios
 *
 * @method getUserByEmail Obtiene usuario por email
 * @method authenticateUser Valida credenciales
 * @method insertUser Crea nuevo usuario
 * @method updatePassword Actualiza contraseña
 * @method isEmailExists Verifica si email está registrado
 * @method deleteAllUsers (DEBUG) Elimina todos los usuarios
 */
@Dao
interface UserDao {

    /**
     * Obtiene usuario por email
     *
     * @param email Email del usuario
     * @return Usuario o null si no existe
     */
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    /**
     * Autentica usuario por email y contraseña (OBSOLETO - usar verificación con hash)
     *
     * @param email Email del usuario
     * @param password Contraseña en texto plano
     * @return Usuario autenticado o null
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun authenticateUser(email: String, password: String): User?

    /**
     * Inserta nuevo usuario
     *
     * @param user Usuario a insertar
     * @return ID del usuario insertado
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    /**
     * Actualiza datos de usuario existente
     *
     * @param user Usuario con datos actualizados
     */
    @Update
    suspend fun updateUser(user: User)

    /**
     * Marca email como verificado y elimina código
     *
     * @param email Email a verificar
     */
    @Query("UPDATE users SET isEmailVerified = 1, verificationCode = NULL WHERE email = :email")
    suspend fun verifyEmail(email: String)

    /**
     * Actualiza código de verificación
     *
     * @param email Email del usuario
     * @param code Nuevo código de verificación
     */
    @Query("UPDATE users SET verificationCode = :code WHERE email = :email")
    suspend fun updateVerificationCode(email: String, code: String)

    /**
     * Verifica código de verificación
     *
     * @param email Email del usuario
     * @param code Código a verificar
     * @return Usuario si código válido, null si no
     */
    @Query("SELECT * FROM users WHERE email = :email AND verificationCode = :code COLLATE NOCASE LIMIT 1")
    suspend fun verifyCode(email: String, code: String): User?

    /**
     * Actualiza contraseña de usuario
     *
     * @param email Email del usuario
     * @param newPassword Nueva contraseña (debe estar hasheada)
     * @return Número de filas actualizadas
     */
    @Query("UPDATE users SET password = :newPassword WHERE email = :email COLLATE NOCASE")
    suspend fun updatePassword(email: String, newPassword: String): Int

    /**
     * Verifica existencia de email
     *
     * @param email Email a verificar
     * @return 1 si existe, 0 si no
     */
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Instancia de [User], o `null` si no se encuentra.
     */
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    /**
     * Elimina todos los usuarios (SOLO PARA PRUEBAS)
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
package com.undef.localhandsbrambillafunes.data.repository

import com.undef.localhandsbrambillafunes.data.dao.UserDao
import com.undef.localhandsbrambillafunes.data.entity.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random
import at.favre.lib.crypto.bcrypt.BCrypt

/**
 * Repositorio para operaciones de autenticación
 *
 * @property userDao DAO para acceso a usuarios
 *
 * @method registerUser Registra nuevo usuario con hash de contraseña
 * @method loginUser Autentica usando BCrypt para verificación
 * @method generateVerificationCode Genera código de 4 dígitos
 * @method updatePassword Actualiza contraseña con hash
 * @method isEmailExists Verifica existencia de email
 */
@Singleton // Garantiza que solo exista una instancia en toda la app
class AuthRepository @Inject constructor(
    private val userDao: UserDao // ← Inyectado por Dagger/Hilt
) {

    /**
     * Registra un nuevo usuario en el sistema
     *
     * Flujo:
     * 1. Verifica si el email ya existe
     * 2. Si no existe, hashea la contraseña
     * 3. Inserta el usuario en la base de datos
     *
     * @param user Objeto User con datos del usuario (contraseña en texto plano)
     * @return Result<Long> con ID del usuario insertado o error
     */
    suspend fun registerUser(user: User): Result<Long> {
        return try {
            // Verificar si el email ya existe
            if (userDao.isEmailExists(user.email) > 0) {
                Result.failure(Exception("El email ya esta registrado!"))
            } else {
                // Hashear la contraseña antes de guardar
                val hashedPassword = BCrypt.withDefaults().hashToString(12, user.password.toCharArray())
                val userWithHashedPassword = user.copy(password = hashedPassword)

                val userId = userDao.insertUser(userWithHashedPassword)
                Result.success(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Autentica un usuario verificando credenciales
     *
     * Flujo:
     * 1. Busca usuario por email
     * 2. Verifica contraseña con BCrypt
     * 3. Comprueba si el email está verificado
     *
     * @param email Email del usuario
     * @param password Contraseña en texto plano
     * @return Result<User> con usuario autenticado o error
     */
    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            // Buscar SOLO por email (no por contraseña)
            val user = userDao.getUserByEmail(email)

            if (user != null) {
                // Verificar contraseña hasheada
                if (BCrypt.verifyer().verify(password.toCharArray(), user.password).verified) {
                    if (user.isEmailVerified) {
                        Result.success(user)
                    } else {
                        Result.failure(Exception("Email no Verificado!"))
                    }
                } else {
                    Result.failure(Exception("Credenciales incorrectos!"))
                }
            } else {
                Result.failure(Exception("Credenciales incorrectos!"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene usuario por email sin verificar contraseña
     *
     * @param email Email a buscar
     * @return Result<User?> con usuario o null si no existe
     */
    suspend fun getUserByemail(email:String): Result<User?> {
        return try {
            val user = userDao.getUserByEmail(email)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Genera y guarda código de verificación
     *
     * @param email Email del usuario a verificar
     * @return Result<String> con código generado
     */
    suspend fun generateVerificationCode(email: String): Result<String> {
        return try {
            val normalizedEmail = email.lowercase()
            val code = generateRandomCode()
            userDao.updateVerificationCode(normalizedEmail, code)
            Result.success(code)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Verifica código de confirmación
     *
     * @param email Email a verificar
     * @param code Código ingresado por usuario
     * @param expectedCode Código esperado (generado previamente)
     * @return Result<Boolean> true si verificación exitosa
     */
    suspend fun verifyCode(email: String, code: String, expectedCode: String): Result<Boolean> {
        return try {

            // Comparar directamente con el código esperado
            if (code == expectedCode) {
                Result.success(true)
            } else {
                Result.failure(Exception("Codigo incorrecto!"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Actualiza contraseña de usuario con hash
     *
     * @param email Email del usuario
     * @param newPassword Nueva contraseña en texto plano
     * @return Result<Boolean> true si actualización exitosa
     */
    suspend fun updatePassword(email: String, newPassword: String): Result<Boolean> {
        val normalizedEmail = email.lowercase() // Normalizar email

        return try {
            // Hashear la nueva contraseña
            val hashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray())

            val rowsUpdated = userDao.updatePassword(normalizedEmail, hashedPassword)
            if (rowsUpdated > 0) {
                Result.success(true)
            } else {
                Result.failure(Exception("No se encontró el usuario con email $email"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Verifica existencia de email en sistema
     *
     * @param email Email a verificar
     * @return Result<Boolean> true si el email existe
     */
    suspend fun isEmailExists(email: String): Result<Boolean> {
        return try {
            val count = userDao.isEmailExists(email)
            Result.success(count > 0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Limpia tabla de usuarios (SOLO PARA PRUEBAS)
     */
    suspend fun clearUsersTable() {
        userDao.deleteAllUsers()
    }

    /**
     * Genera código numérico aleatorio de 4 dígitos
     *
     * @return String con código entre 1000-9999
     */
    private fun generateRandomCode(): String {
        return Random.nextInt(1000, 9999).toString()
    }

    /**
     * Verifica código de reset para recuperación de contraseña
     *
     * @param email Email del usuario
     * @param code Código a verificar
     * @return Result<Boolean> true si código válido
     */
    suspend fun verifyResetCode(email: String, code: String): Result<Boolean> {
        return try {
            val normalizedEmail = email.lowercase()
            val user = userDao.verifyCode(normalizedEmail, code)
            if (user != null) {
                Result.success(true)
            } else {
                Result.failure(Exception("Código incorrecto"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
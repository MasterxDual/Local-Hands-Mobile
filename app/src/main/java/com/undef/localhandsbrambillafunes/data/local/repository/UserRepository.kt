package com.undef.localhandsbrambillafunes.data.local.repository

import com.undef.localhandsbrambillafunes.data.local.dao.UserDao
import com.undef.localhandsbrambillafunes.data.local.entities.User

/**
 * Repositorio que actúa como intermediario entre la capa de datos (DAO) y la lógica de negocio.
 *
 * Proporciona una interfaz abstracta para acceder a operaciones relacionadas con usuarios,
 * y delega las llamadas al [UserDao].
 *
 * @property userDao Objeto DAO que proporciona acceso a los métodos de la base de datos.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Inserta un nuevo usuario.
     *
     * @param user Instancia del usuario.
     * @return ID generado por la base de datos.
     */
    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param user Usuario actualizado.
     */
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    /**
     * Elimina un usuario.
     *
     * @param user Usuario a eliminar.
     */
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Instancia de [User], o `null` si no se encuentra.
     */
    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico.
     * @return Instancia de [User], o `null` si no existe.
     */
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}

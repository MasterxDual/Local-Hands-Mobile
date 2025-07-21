package com.undef.localhandsbrambillafunes.data.exception

/**
 * Excepción lanzada cuando se requiere autenticación pero no hay usuario logueado
 *
 * @param message Mensaje descriptivo del error
 */
class NotAuthenticatedException(message: String) : Exception(message)
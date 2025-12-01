package org.ing.interfaces

import org.ing.model.ConteoPorPermiso
import org.ing.model.UsuarioModel

interface IUsuarioRepository {
    suspend fun crearUsuario(nombre: String, edad: Int, permisos: String): UsuarioModel
    suspend fun obtenerTodo(): List<UsuarioModel>
    suspend fun borrarUsuario(id: Long)
    suspend fun obtenerPorId(id: Long): UsuarioModel
    suspend fun contarUsuarioPorPermiso(): List<ConteoPorPermiso>
}
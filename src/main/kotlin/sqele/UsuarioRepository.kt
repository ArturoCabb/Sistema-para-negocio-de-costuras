package org.ing.sqele

import org.ing.entity.PermisoEntity
import org.ing.entity.UsuarioEntity
import org.ing.interfaces.IUsuarioRepository
import org.ing.model.ConteoPorPermiso
import org.ing.model.UsuarioModel
import org.ing.tables.PermisoTable
import org.ing.tables.UsuarioTable
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.count

class UsuarioRepository : IUsuarioRepository {

    override suspend fun crearUsuario(
        nombre: String,
        edad: Int,
        permiso: String?,
        contrasena: String
    ): UsuarioModel = QueryHelper().dbQuery {
        val permisoId = permiso?.let { p-> PermisoEntity.find { PermisoTable.permiso eq p }.singleOrNull() }
        val entity = UsuarioEntity.new {
            this.nombre = nombre
            this.edad = edad
            if (permisoId != null) {
                this.permisoId = permisoId.id
            } else {
            this.permisoId = null
            }
            this.esta_activo = true
            this.contrasena = hashPassword(contrasena)
        }
        entity.toDomain()
    }

    override suspend fun obtenerTodo(): List<UsuarioModel> = QueryHelper().dbQuery {
        UsuarioEntity.Companion.all().map { it.toDomain() }
    }

    override suspend fun borrarUsuario(id: Long) = QueryHelper().dbQuery {
        UsuarioEntity.Companion.findById(id)?.delete() ?: Unit
    }

    override suspend fun obtenerPorId(id: Long): UsuarioModel = QueryHelper().dbQuery {
        UsuarioEntity.Companion.findById(id)?.toDomain() as UsuarioModel
    }

    override suspend fun contarUsuarioPorPermiso(): List<ConteoPorPermiso> = QueryHelper().dbQuery {
        val countAlias = UsuarioTable.id.count().alias("total")

        UsuarioTable
                // 🔑 Realizar el JOIN a la tabla PermisoTable
            .join(PermisoTable, JoinType.LEFT, onColumn = UsuarioTable.id, otherColumn = PermisoTable.id)
            .select(PermisoTable.permiso, countAlias) // 🔑 Seleccionar el campo String del Permiso
            .groupBy(PermisoTable.permiso) // 🔑 Agrupar por el campo String
            .map { row ->
                // Manejo de valores NULL (para trabajadores sin permiso)
                val permisoNombre = row.getOrNull(PermisoTable.permiso) ?: "Sin Permiso"
                ConteoPorPermiso(
                    permiso = permisoNombre,
                    total = row[countAlias]
                )
            }
    }

    private fun hashPassword(password: String): String {
        return "$password-hashed" // BCrypt.hashpw(pass, BCrypt.gensalt())
    }
}
package org.ing.sqele

import kotlinx.coroutines.Dispatchers
import org.ing.entity.UsuarioEntity
import org.ing.interfaces.IUsuarioRepository
import org.ing.model.ConteoPorPermiso
import org.ing.model.UsuarioModel
import org.ing.tables.TrabajadorTable
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UsuarioRepository : IUsuarioRepository {

    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun crearUsuario(
        nombre: String,
        edad: Int,
        permisos: String
    ): UsuarioModel = dbQuery {
        val entity = UsuarioEntity.Companion.new {
            this.nombre = nombre
            this.edad = edad
            this.permisos = permisos
        }
        entity.toDomain()
    }

    override suspend fun obtenerTodo(): List<UsuarioModel> = dbQuery {
        UsuarioEntity.Companion.all().map { it.toDomain() }
    }

    override suspend fun borrarUsuario(id: Long) = dbQuery {
        UsuarioEntity.Companion.findById(id)?.delete() ?: Unit
    }

    override suspend fun obtenerPorId(id: Long): UsuarioModel = dbQuery {
        UsuarioEntity.Companion.findById(id)?.toDomain() as UsuarioModel
    }

    override suspend fun contarUsuarioPorPermiso(): List<ConteoPorPermiso> = dbQuery {
        val countAlias = TrabajadorTable.id.count().alias("total")

        TrabajadorTable
            .select(TrabajadorTable.permisos, countAlias)
            .groupBy(TrabajadorTable.permisos)
            .map {
                row ->
                ConteoPorPermiso(
                    permiso = row[TrabajadorTable.permisos],
                    total = row[countAlias]
                )
            }
    }
}
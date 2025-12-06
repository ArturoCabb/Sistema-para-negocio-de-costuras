package org.ing.sqele

import org.ing.entity.PermisoEntity
import org.ing.model.PermisoModel
import org.ing.tables.PermisoTable

class PermisosRepository {

    suspend fun crearPermiso(nombre: String): PermisoModel = QueryHelper().dbQuery {
        val entity = PermisoEntity.new {
            this.permiso = nombre
        }
        PermisoModel(entity.id.value, entity.permiso)
    }

    suspend fun obtenerPermisos(): List<PermisoModel> = QueryHelper().dbQuery {
        PermisoEntity.Companion.all().map { PermisoModel(it.id.value, it.permiso) }
    }

    suspend fun obtenerPermiso(nombre: String): PermisoModel = QueryHelper().dbQuery {
        PermisoEntity.find { PermisoTable.permiso eq nombre  }.singleOrNull()?.let { PermisoModel(it.id.value, it.permiso) } as PermisoModel
    }
}
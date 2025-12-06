package org.ing.entity

import org.ing.model.UsuarioModel
import org.ing.tables.UsuarioTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UsuarioEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UsuarioEntity>(UsuarioTable)

    var nombre by UsuarioTable.nombre
    var contrasena by UsuarioTable.contrasena
    var edad by UsuarioTable.edad
    var permisoId by UsuarioTable.permiso
    val permisoRef: PermisoEntity? by PermisoEntity optionalReferencedOn UsuarioTable.permiso
    var fecha_creacion by UsuarioTable.fecha_creacion
    var esta_activo by UsuarioTable.esta_activo

    fun toDomain() = UsuarioModel(id.value, nombre, edad, permisoRef?.permiso ?: "Sin permiso", fecha_creacion, esta_activo)
}
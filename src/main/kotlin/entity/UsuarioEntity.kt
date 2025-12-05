package org.ing.entity

import org.ing.model.UsuarioModel
import org.ing.tables.TrabajadorTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UsuarioEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UsuarioEntity>(TrabajadorTable)

    var nombre by TrabajadorTable.nombre
    var edad by TrabajadorTable.edad
    var permisos by TrabajadorTable.permisos
    var fecha_creacion by TrabajadorTable.fecha_creacion
    var esta_activo by TrabajadorTable.esta_activo

    fun toDomain() = UsuarioModel(id.value, nombre, edad, permisos.value, fecha_creacion, esta_activo)
}
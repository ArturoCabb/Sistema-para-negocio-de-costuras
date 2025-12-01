package org.ing.entity

import org.ing.model.UsuarioModel
import org.ing.tables.UsuarioTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UsuarioEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UsuarioEntity>(UsuarioTable)

    var nombre by UsuarioTable.nombre
    var edad by UsuarioTable.edad
    var permisos by UsuarioTable.permisos

    fun toDomain() = UsuarioModel(id.value, nombre, edad, permisos)
}
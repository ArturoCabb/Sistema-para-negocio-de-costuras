package org.ing.entity

import org.ing.tables.PermisoTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PermisoEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PermisoEntity>(PermisoTable)

    var permiso by PermisoTable.permiso
}
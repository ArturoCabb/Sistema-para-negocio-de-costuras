package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table

object PermisoTable : LongIdTable("Permiso") {
    val permiso = varchar("permiso", 255)
}
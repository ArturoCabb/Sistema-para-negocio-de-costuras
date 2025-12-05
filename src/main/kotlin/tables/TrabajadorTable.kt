package org.ing.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

object TrabajadorTable : LongIdTable("Trabajador") {
    val nombre = varchar("nombre", 255)
    val edad = integer("edad")
    val permisos = reference(
        name = "permisos",
        foreign = PermisoTable,
        onDelete = ReferenceOption.RESTRICT
    )
    val fecha_creacion = datetime("fecha_creacion")
    val esta_activo = bool("esta_activo")
}
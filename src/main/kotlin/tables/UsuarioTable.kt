package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UsuarioTable : LongIdTable("Trabajador") {
    val nombre = varchar("nombre", 255)
    val edad = integer("edad")
    val permiso = reference(
        name = "permisos",
        foreign = PermisoTable,
        onDelete = ReferenceOption.SET_NULL
    ).nullable()
    val contrasena = varchar("contrasena", 255)
    val fecha_creacion = datetime("fecha_creacion").clientDefault{ LocalDateTime.now() }
    val esta_activo = bool("esta_activo")
}
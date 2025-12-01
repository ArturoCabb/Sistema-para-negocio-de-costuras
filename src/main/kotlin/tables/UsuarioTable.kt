package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UsuarioTable : LongIdTable("usuarios") {
    val nombre = varchar("nombre", 50)
    val edad = integer("edad")
    val permisos = varchar("permisos", 50)
}
package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object OrdenTable : LongIdTable("Orden") {
    val trabajador_id = reference(
        name = "id",
        foreign = TrabajadorTable,
        onDelete = ReferenceOption.RESTRICT
    )
    val cliente = varchar("cliente", 255)
    val estado = varchar("estado", 255)
    val fecha_registro = datetime("fecha_registro")
    val fecha_completado = datetime("fecha_completado")
}
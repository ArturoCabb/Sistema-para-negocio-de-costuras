package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object PrendaTable : LongIdTable("Prenda") {
    val orden_id = reference(
        name = "orden_id",
        foreign = OrdenTable,
        onDelete = ReferenceOption.RESTRICT
    )
    val fecha_registro = datetime("fecha_registro")
    val fecha_completado = datetime("fecha_completado")
    val descripcion_de_trabajo = varchar("descripcion_de_trabajo", 255)
    val costo_trabajo = double("costo_trabajo")
    val estado = varchar("estado", 255)
}
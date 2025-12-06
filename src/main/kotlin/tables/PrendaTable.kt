package org.ing.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object PrendaTable : LongIdTable("Prenda") {
    val orden_id = reference(
        name = "orden_id",
        foreign = OrdenTable,
        onDelete = ReferenceOption.CASCADE
    )
    val fecha_registro = datetime("fecha_registro").clientDefault { LocalDateTime.now() }
    val fecha_completado = datetime("fecha_completado").nullable()
    val descripcion_de_trabajo = varchar("descripcion_de_trabajo", 255)
    val costo_trabajo = decimal("costo_trabajo", 15, 2)
    val estado = varchar("estado", 255)
}
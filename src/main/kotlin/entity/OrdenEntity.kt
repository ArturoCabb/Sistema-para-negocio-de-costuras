package org.ing.entity

import org.ing.model.OrdenModel
import org.ing.tables.OrdenTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class OrdenEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<OrdenEntity>(OrdenTable)

    var trabajadorId by OrdenTable.trabajador_id
    var trabajadorNombre by UsuarioEntity referencedOn OrdenTable.trabajador_id
    var cliente by OrdenTable.cliente
    var estado by OrdenTable.estado
    var fechaRegistro by OrdenTable.fecha_registro
    var fechaCompletado by OrdenTable.fecha_completado

    fun toDomain() = OrdenModel(id.value, trabajadorId.value, trabajadorNombre.nombre, cliente, estado, fechaRegistro, fechaCompletado)
}
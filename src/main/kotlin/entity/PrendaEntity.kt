package org.ing.entity

import org.ing.model.PrendaModel
import org.ing.tables.PrendaTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PrendaEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PrendaEntity>(PrendaTable)

    var ordenId by PrendaTable.orden_id
    var fechaRegistro by PrendaTable.fecha_registro
    var fechaCompletado by PrendaTable.fecha_completado
    var descripcionDeTrabajo by PrendaTable.descripcion_de_trabajo
    var costoTrabajo by PrendaTable.costo_trabajo
    var estado by PrendaTable.estado

    fun toDomain() = PrendaModel(id.value, ordenId.value, fechaRegistro, fechaCompletado, descripcionDeTrabajo, costoTrabajo.toDouble(), estado)
}
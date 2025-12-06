package org.ing.sqele

import org.ing.entity.OrdenEntity
import org.ing.entity.PrendaEntity
import org.ing.model.OrdenModel
import org.ing.model.PrendaModel
import org.ing.tables.OrdenTable
import org.ing.tables.PrendaTable
import org.ing.tables.UsuarioTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime

class OrdenRepository {

    suspend fun crearOrden(trabajadorId: Long, cliente: String, estado: String, fechaRegistro: LocalDateTime? = null): OrdenModel = QueryHelper().dbQuery {
        val entity = OrdenEntity.new {
            this.trabajadorId = EntityID(trabajadorId, OrdenTable)
            this.cliente = cliente
            this.estado = estado
            if (fechaRegistro != null)
                this.fechaRegistro = fechaRegistro
        }
        entity.toDomain()
    }

    suspend fun crearPrenda(ordenId: Long, fechaRegistro: LocalDateTime? = null, descripcionDeTrabajo: String, costoTrabajo: Double, estado: String) : PrendaModel =
        QueryHelper().dbQuery {
            val entity = PrendaEntity.new {
                this.ordenId = EntityID(ordenId, OrdenTable)
                if (fechaRegistro != null)
                    this.fechaRegistro = fechaRegistro
                this.descripcionDeTrabajo = descripcionDeTrabajo
                this.costoTrabajo = costoTrabajo.toBigDecimal()
                this.estado = estado
            }
            entity.toDomain()
        }

    suspend fun actualizarPrenda(id: Long, fechaCompletado: LocalDateTime?, estado: String): PrendaModel? = QueryHelper().dbQuery {
        PrendaEntity.find { PrendaTable.id eq id and PrendaTable.estado.isDistinctFrom("terminado")  }.firstOrNull()?.toDomain()
    }

    suspend fun obtenerOrdenes() : List<OrdenModel> = QueryHelper().dbQuery {
        OrdenEntity.Companion.all().map {
            OrdenModel(
                it.id.value,
                it.trabajadorId.value,
                it.trabajadorNombre.nombre,
                it.cliente,
                it.estado,
                it.fechaRegistro,
                it.fechaCompletado
            )
        }
    }

    suspend fun obtenerOrdenPorId(id: Long): OrdenModel = QueryHelper().dbQuery {
        OrdenEntity.findById(id)?.toDomain() as OrdenModel
    }

    suspend fun obtenerPrendasPorIdOrden(id: Long): List<PrendaModel> = QueryHelper().dbQuery {
        PrendaEntity.find{ PrendaTable.orden_id eq id  }.map { PrendaModel(
            it.id.value,
            it.ordenId.value,
            it.fechaRegistro,
            it.fechaCompletado,
            it.descripcionDeTrabajo,
            it.costoTrabajo.toDouble(),
            it.estado) }
    }

    suspend fun actualizarOrden(id: Long, estado: String, fechaCompletado: LocalDateTime?): OrdenModel? = QueryHelper().dbQuery {
        val orden = OrdenEntity.findById(id)
        orden?.apply {
            this.estado = estado
            this.fechaCompletado = fechaCompletado
        }?.toDomain()
    }

}
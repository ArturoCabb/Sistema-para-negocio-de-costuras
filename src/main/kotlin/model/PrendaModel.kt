package org.ing.model

import java.time.LocalDateTime

data class PrendaModel(
    val prenda_id: Long,
    val orden_id: Long,
    val fecha_registro: LocalDateTime,
    val fecha_completado: LocalDateTime?,
    val descripcion_de_trabajo: String,
    val costo_trabajo: Double,
    val estado: String,
)

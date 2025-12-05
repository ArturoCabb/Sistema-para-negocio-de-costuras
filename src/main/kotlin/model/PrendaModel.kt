package org.ing.model

import java.time.LocalDateTime

data class PrendaModel(
    val orden_id: Long,
    val fecha_registro: LocalDateTime,
    val fecha_completado: LocalDateTime,
    val descripcion_de_trabajo: LocalDateTime,
    val costo_trabajo: Double,
    val estado: String,
)

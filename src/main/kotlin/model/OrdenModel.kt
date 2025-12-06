package org.ing.model

import java.time.LocalDateTime

data class OrdenModel(
    val id: Long,
    val trabajador_id: Long,
    val trabajador_nombre: String? = null,
    val cliente: String,
    val estado: String,
    val fecha_registro: LocalDateTime,
    val fecha_completado: LocalDateTime?
)

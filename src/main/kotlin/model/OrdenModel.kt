package org.ing.model

import java.util.Date

data class OrdenModel(
    val id: Long,
    val trabajador_id: Long,
    val cliente: String,
    val estado: String,
    val fecha_registro: Date,
    val fecha_completado: Date
)

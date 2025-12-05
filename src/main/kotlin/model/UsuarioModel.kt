package org.ing.model

import java.time.LocalDateTime

data class UsuarioModel(
    val id: Long,
    val nombre: String,
    val edad: Int,
    val permisos: Long,
    val fecha_creacion: LocalDateTime,
    val esta_activo: Boolean
)

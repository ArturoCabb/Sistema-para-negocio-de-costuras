package org.ing

import org.ing.controller.UserDataBaseController
import org.ing.sqele.UsuarioRepository

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    // Iniciamos la conexion
    DatabaseFactory.mariaDB()

    // Inicio dependencias
    val repository = UsuarioRepository()
    UserDataBaseController(repository).iniciar()

    DatabaseFactory.cerrarMariaDB()
}

fun realizarVentas() {
    val ventasLimpieza: Ventas = VenderLimpieza()
    val ventasRopa: Ventas = VenderRopa()
    ventasLimpieza.vender()
    ventasRopa.vender()
}
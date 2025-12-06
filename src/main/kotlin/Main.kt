package org.ing

import org.ing.controller.OrdenController
import org.ing.controller.PermisosController
import org.ing.controller.UserDataBaseController
import org.ing.sqele.OrdenRepository
import org.ing.sqele.PermisosRepository
import org.ing.sqele.UsuarioRepository

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    // Iniciamos la conexion
    DatabaseFactory.mariaDB()

    // Inicio dependencias
    while (true) {
        println("Que desea hacer\n" + "1. Manejar usuarios\n" + "2. Manejar permisos\n" + "3. Manejar ordenes\n" + "4. Salir")
        val opcion = readlnOrNull()
        if (opcion != null) {
            when (opcion.toInt()) {
                1 -> {
                    val repository = UsuarioRepository()
                    UserDataBaseController(repository).iniciar()
                }

                2 -> {
                    val repository = PermisosRepository()
                    PermisosController(repository).iniciar()
                }

                3 -> {
                    val repository = OrdenRepository()
                    OrdenController(repository).iniciar()
                }
                4 -> {
                    println("Saliendo")
                    break
                }
                else -> println("Opcion no valida")
            }
        }
    }


    DatabaseFactory.cerrarMariaDB()
}

fun realizarVentas() {
    val ventasLimpieza: Ventas = VenderLimpieza()
    val ventasRopa: Ventas = VenderRopa()
    ventasLimpieza.vender()
    ventasRopa.vender()
}
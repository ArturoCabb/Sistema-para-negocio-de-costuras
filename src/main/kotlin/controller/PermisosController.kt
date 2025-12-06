package org.ing.controller

import kotlinx.coroutines.runBlocking
import org.ing.sqele.PermisosRepository

class PermisosController(private val repositorio: PermisosRepository) {
    fun iniciar() = runBlocking {
        var ejecutando = true

        while (ejecutando) {
            val mostrarOpciones = "Seleccione una opcion\n" + "1. Crear Permiso\n" + "2. Mostrar Permisos\n" + "3. Obtener Permiso por Nombre\n" + "4. Salir"
            println(mostrarOpciones)
            val opcion = readlnOrNull()?.toInt() ?: 0

            when(opcion) {
                1 -> crearPermiso()
                2 -> mostrarPermisos()
                3 -> obtenerPermiso()
                4 -> {
                    println("Regresando al menu principal")
                    ejecutando = false
                }
                else -> println("Opcion no valida")
            }
        }
    }

    private suspend fun crearPermiso() {
        println("Ingrese el nombre del permiso:")
        val nombre = readlnOrNull() ?: ""

        if (nombre.isBlank()) {
            println("El nombre del permiso no puede estar vacío.")
            return
        }

        try {
            val nuevoPermiso = repositorio.crearPermiso(nombre)
            println("Permiso '${nuevoPermiso.permiso}' con ID ${nuevoPermiso.id} creado exitosamente.")
        } catch (e: Exception) {
            println("Error al crear el permiso: ${e.message}")
        }
    }

    private suspend fun mostrarPermisos() {
        println("Listado de Permisos:")
        try {
            val permisos = repositorio.obtenerPermisos()
            if (permisos.isEmpty()) {
                println("No hay permisos registrados.")
            } else {
                permisos.forEach { println("ID: ${it.id}, Nombre: ${it.permiso}") }
            }
        } catch (e: Exception) {
            println("Error al obtener los permisos: ${e.message}")
        }
    }

    private suspend fun obtenerPermiso() {
        println("Ingrese el nombre del permiso a buscar:")
        val nombre = readlnOrNull() ?: ""

        if (nombre.isBlank()) {
            println("El nombre del permiso no puede estar vacío.")
            return
        }

        try {
            val permiso = repositorio.obtenerPermiso(nombre)
            if (permiso != null) {
                println("Permiso encontrado: ID: ${permiso.id}, Nombre: ${permiso.permiso}")
            } else {
                println("No se encontró un permiso con el nombre '$nombre'.")
            }
        } catch (e: Exception) {
            println("Error al obtener el permiso: ${e.message}")
        }
    }
}
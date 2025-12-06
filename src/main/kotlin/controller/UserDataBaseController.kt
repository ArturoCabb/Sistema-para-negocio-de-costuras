package org.ing.controller

import kotlinx.coroutines.runBlocking
import org.ing.interfaces.IUsuarioRepository

class UserDataBaseController(private val repositorio: IUsuarioRepository) {
    fun iniciar() = runBlocking {
        var ejecutando = true

        while (ejecutando) {
            val mostrarOpciones = "Seleccione una opcion\n" + "1. Agregar Usuario\n" + "2. Mostrar Usuarios\n" + "3. Borrar Usuario\n" + "4. Obtener por Id\n" + "5. Contar permisos\n" + "6. Salir"
            println(mostrarOpciones)
            val opcion = readlnOrNull()?.toInt() ?: 0

            when(opcion) {
                1 -> agregarUsuario()
                2 -> mostrarUsuarios()
                3 -> borrarUsuario()
                4 -> obtenerPorId()
                5 -> contarPermisos()
                6 -> {
                    println("Saliendo")
                    ejecutando = false
                }
                else -> println("Opcion no valida")
            }
        }
    }

    private suspend fun agregarUsuario() {
        println("Ingrese nombre")
        val nombre = readlnOrNull() ?: ""

        println("Ingrese edad")
        val edad = readlnOrNull()?.toInt() ?: 0

        println("Ingrese permisos")
        val permisos = readlnOrNull()

        if (nombre.isBlank() || edad <= 0) {
            println("Datos invalidos")
            return
        }

        try {
            repositorio.crearUsuario(nombre, edad, permisos, "contrasena")
        } catch (e : Exception) {
            println("Hubo un problema ${e.message}")
        }
    }

    private suspend fun mostrarUsuarios() {
        println("Listando Usuarios")
        val lista = repositorio.obtenerTodo()
        println(lista.forEach { u -> println("%-5d | %-20s | %-5d | %-5s".format(u.id, u.nombre, u.edad, u.permiso)) })
    }

    private suspend fun borrarUsuario() {
        println("Ingrese el id")
        val id = readlnOrNull()?.toLong() ?: 0
        repositorio.borrarUsuario(id)
        println("El id $id ha sido borrado")
    }

    private suspend fun obtenerPorId() {
        println("Obteneindo id")
        val id = readlnOrNull()?.toLong() ?: 0
        try {
            val u = repositorio.obtenerPorId(id)
            println("%-5d | %-20s | %-5d | %-5s".format(u.id, u.nombre, u.edad, u.permiso))
        } catch (_ : NullPointerException) {
            println("No se encontro el usuario")
        }
    }

    private suspend fun contarPermisos() {
        println("Contando permisos")
        val conteos = repositorio.contarUsuarioPorPermiso()
        conteos.forEach { println("%-5s | %-5d".format(it.permiso, it.total)) }
    }
}
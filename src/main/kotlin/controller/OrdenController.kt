package org.ing.controller

import kotlinx.coroutines.runBlocking
import org.ing.model.PrendaModel
import org.ing.sqele.OrdenRepository
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrdenController(private val repositorio: OrdenRepository) {
    fun iniciar() = runBlocking {
        var ejecutando = true
        while (ejecutando) {
            println(
                "Seleccione una opcion\n" + "1. Crear una nueva orden de prenda\n" +
                "2. Consultar una orden\n" + "3. Actualizar una orden\n" +
                "4. Eliminar una orden\n" + "5. Salir"
            )
            val opcion = readlnOrNull()?.toInt() ?: 0
            when(opcion) {
                1 -> crearOrden()
                2 -> consultarOrden()
                3 -> actualizarOrden()
                4 -> eliminarOrden()
                5 -> {
                    println("Saliendo")
                    ejecutando = false
                }
                else -> println("Opcion no valida")
            }
        }
    }

    private suspend fun crearOrden() {
        println("Ingrese el id del trabajador")
        val trabajadorId = readlnOrNull()?.toLong()
        if (trabajadorId == null) println("No puede crearse una orden sin saber quien la crea")
        else {
            println("Ingrese el cliente")
            val cliente = readlnOrNull()
            if (cliente.isNullOrBlank()) {
                println("No puede crearse una orden sin saber el cliente")
                return
            }
            val estado = "Pendiente"
            try {
                val nuevaOrden = repositorio.crearOrden(trabajadorId, cliente, estado)
                while (true) {
                    println("Quiere agregar una prenda> SI  / NO")
                    val res = readlnOrNull()
                    if (res?.lowercase().equals("si")) {
                        println("Ingrese la descripcion de la prenda")
                        val descripcionDeTrabajo = readlnOrNull()
                        if (descripcionDeTrabajo.isNullOrBlank()) println("Debe ingresar la descripcion de la prenda")
                        else {
                            println("Ingrese el costo de la prenda")
                            val costoTrabajo = readlnOrNull()?.toDouble()
                            if (costoTrabajo == null) println("Debe ingresar el costo de la prenda")
                            else {
                                val prendaRes =
                                    crearOrdenPrenda(nuevaOrden.id, descripcionDeTrabajo, costoTrabajo, estado)
                                println("Creando prenda con el numero de orden ${nuevaOrden.id} y numero de prenda ${prendaRes.prenda_id}")
                            }
                        }
                    } else {
                        break
                    }
                }
            } catch (e : Exception) {
                println("Error al crear la orden: ${e.message}")
            }
        }
    }

    private suspend fun crearOrdenPrenda(orenId: Long, descripcionDeTrabajo: String, costoTrabajo: Double, estado: String): PrendaModel {
        val nuevaPrenda: PrendaModel
        try{
        nuevaPrenda = repositorio.crearPrenda(orenId, descripcionDeTrabajo = descripcionDeTrabajo, costoTrabajo = costoTrabajo, estado = estado)
        } catch (e: Exception) {
            throw e
        }
        return nuevaPrenda
    }

    private suspend fun consultarOrden(): List<PrendaModel> {
        try {
            val ordenes = repositorio.obtenerOrdenes()
            if (ordenes.isEmpty()) {
                println("No hay ordenes registradas.")
            } else {
                println("Listado de Ordenes:")
                ordenes.forEach { orden ->
                    println("ID: ${orden.id}, Empleado: ${orden.trabajador_nombre}, Cliente: ${orden.cliente}, Estado: ${orden.estado}, Fecha rgistro: ${orden.fecha_registro}, Fecha finalizado: ${orden.fecha_completado ?: "No finalizado"}")
                }

                println("Ingrese el id de la orden")
                val id = readlnOrNull()?.toLong()
                if (id == null) println("No puede crearse una orden sin saber quien la crea")
                else {
                    val encabezado = String.format(
                        "%-5s | %-5s | %-20s | %-12s | %-12s | %-5s | %-10s",
                        "ID", "Orden", "Descripción", "Estado", "Fecha", "Trab.", "Costo"
                    )
                    println(encabezado)
                    println("-".repeat(85))
                    val respuesta = repositorio.obtenerPrendasPorIdOrden(id)
                    println(respuesta.forEach { u ->
                        println(
                            "%-5d | %-5d | %-20s | %-20s | %-20s | %-10.2f | %-10s".format(
                                u.prenda_id,
                                u.orden_id,
                                u.fecha_registro.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                u.fecha_completado?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                u.descripcion_de_trabajo,
                                u.costo_trabajo,
                                u.estado
                            )
                        )
                    })
                    return respuesta
                }
            }
        } catch (e: Exception) {
            println("Error al obtener la orden: ${e.message}")
        }
        return emptyList()
    }

    private suspend fun actualizarOrden() {
        try {
            consultarOrden()
            println("Ingrese el id de la prenda")
            val id = readlnOrNull()?.toLong()
            if (id == null) println("Debe de ingresar un id de Prenda")
            else {
                println("Ingrese el estado de la prenda")
                val estado = readlnOrNull()
                if (estado.isNullOrBlank()) println("Debe de ingresar un estado")
                else if (estado.lowercase() == ("terminado")) {
                    val respuesta = repositorio.actualizarPrenda(id, LocalDateTime.now(), estado)
                } else {
                    val respuesta = repositorio.actualizarPrenda(id, null, estado)
                    if (respuesta == null) {
                        println("No se puede actualizar una prenda terminada")
                    }
                }
            }
        } catch (e: Exception) {
            println("Error al actualizar la orden: ${e.message}")
        }
    }

    private suspend fun eliminarOrden() {
        println("Actualmente no se puede eliminar una orden, solo se puede actualizar el estado a concluido")
    }

}
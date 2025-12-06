package org.ing

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.ing.tables.OrdenTable
import org.ing.tables.PermisoTable
import org.ing.tables.PrendaTable
import org.ing.tables.UsuarioTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private var dataSource: HikariDataSource? = null
    fun sqlite() {
        val driverClassName = "org.sqlite.JDBC"
        val jdbcUrl = "jdbc:sqlite:base_datos_mom.db"

        val database = Database.connect(jdbcUrl, driverClassName)
    }

    fun mariaDB() {
        val driverClassName = "org.mariadb.jdbc.Driver"
        val jdbcUrl = "jdbc:mariadb://192.168.1.70/base_datos_mom"
        val userName = "artur"
        val password = "DannaXimena"
        fun connectDatabase() {
            val database = Database.connect(jdbcUrl, driverClassName, userName, password)
            transaction(database) {
                SchemaUtils.create(PermisoTable, UsuarioTable, PrendaTable, OrdenTable)
            }
        }
        fun conectDataSource() {
            val config = HikariConfig().apply {
                this.jdbcUrl = jdbcUrl
                this.username = userName
                this.password = password
                this.driverClassName = driverClassName

                validate()
            }

            dataSource = HikariDataSource(config)
            val database = Database.connect(dataSource!!)
            transaction(database) {
                SchemaUtils.create(PermisoTable, UsuarioTable, PrendaTable, OrdenTable)
            }
        }
        conectDataSource()
    }

    fun cerrarMariaDB() {
        dataSource?.close()
    }
}
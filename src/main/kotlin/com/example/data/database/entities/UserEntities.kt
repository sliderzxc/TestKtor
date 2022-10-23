package com.example.data.database.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


object UserTable: Table("main_user_table") {

    val id: Column<Int> = integer("id").autoIncrement()
    val username: Column<String> = varchar("username", 50)
    val isInChat: Column<Boolean> = bool("isInChat")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
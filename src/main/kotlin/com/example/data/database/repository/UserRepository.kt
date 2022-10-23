package com.example.data.database.repository

import com.example.data.database.dao.UserDao
import com.example.data.database.database.DatabaseFactory
import com.example.data.database.entities.UserTable
import com.example.data.entities.User
import com.example.data.entities.UserDraft
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserRepository : UserDao {

    override suspend fun addUser(userDraft: UserDraft): User? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = UserTable.insert { user ->
                user[username] = userDraft.username
                user[isInChat] = userDraft.isInChat
            }
        }
        return rowToUser(statement?.resultedValues?.get(0))
    }

    override suspend fun getUser(id: Int): User? {
        return DatabaseFactory.dbQuery {
            UserTable.select { UserTable.id.eq(id) }
                .map {
                    rowToUser(it)
                }.singleOrNull()
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return DatabaseFactory.dbQuery {
            UserTable.selectAll().mapNotNull {
                rowToUser(it)
            }
        }
    }

    override suspend fun deleteUser(id: Int): Int {
        return DatabaseFactory.dbQuery {
            UserTable.deleteWhere { UserTable.id.eq(id) }
        }
    }

    override suspend fun updateUser(id: Int, userDraft: UserDraft): Int {
        return DatabaseFactory.dbQuery {
            UserTable.update({ UserTable.id.eq(id) }) { user ->
                user[username] = userDraft.username
                user[isInChat] = userDraft.isInChat
            }
        }
    }

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null)
            return null
        return User(
            id = row[UserTable.id],
            username = row[UserTable.username],
            isInChat = row[UserTable.isInChat]
        )
    }
}
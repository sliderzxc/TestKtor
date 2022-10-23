package com.example.data.database.dao

import com.example.data.entities.User
import com.example.data.entities.UserDraft

interface UserDao {

    suspend fun getUser(id: Int): User?

    suspend fun getAllUsers(): List<User>?

    suspend fun updateUser(id: Int, userDraft: UserDraft): Int?

    suspend fun deleteUser(id: Int): Int?

    suspend fun addUser(userDraft: UserDraft): User?
}
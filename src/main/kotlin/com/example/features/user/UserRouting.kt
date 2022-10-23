package com.example.features.user

import com.example.data.database.entities.UserTable.id
import com.example.data.database.repository.UserRepository
import com.example.data.entities.User
import com.example.data.entities.UserDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutingConfiguration() {

    routing {
        val repository = UserRepository()

        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@get
            }
            val user: User? = repository.getUser(id)
            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "User was not found"
                )
                return@get
            }
            call.respond(user)
        }

        get("/users") {
            val users = repository.getAllUsers()
            call.respond(users)
        }

        put("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val userDraft = call.receive<UserDraft>()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@put
            }
            val updated = repository.updateUser(id, userDraft)
            if (updated != 0) {
                repository.updateUser(id, userDraft)
                call.respond(
                    HttpStatusCode.OK,
                    "This user was updated"
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "User with id $id was not found"
                )
            }
        }

        delete("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@delete
            }
            val deleted = repository.deleteUser(id)
            if (deleted != 0) {
                call.respond(
                    HttpStatusCode.OK,
                    "This user was deleted"
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "User with id:$id was not found"
                )
            }
        }

        post("/users") {
            val userDraft = call.receive<UserDraft>()
            val user = repository.addUser(userDraft)
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "User with id:$id was not found"
                )
            }
        }
    }

}
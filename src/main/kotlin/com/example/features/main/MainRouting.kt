package com.example.features.main

import com.example.features.user.userRoutingConfiguration
import io.ktor.server.application.*

fun Application.mainRoutingConfiguration() {
    userRoutingConfiguration()
}
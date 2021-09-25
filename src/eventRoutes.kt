package com.lucasob

import com.lucasob.slack.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Route.eventRoutes() {
    post("/event") {

        // If it is a challenge, respond as required
        challengeResponse(call)?.let { return@post call.respondText { it } }

        // If it is a message, just act on it
        messageResponse(call)

        // Slack requires that all successful *delivery* be flagged as 200
        call.respond(HttpStatusCode.OK)
    }
}
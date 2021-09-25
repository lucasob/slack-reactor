package com.lucasob

import com.lucasob.slack.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

fun Route.eventRoutes() {
    post("/event") {

        val log = LoggerFactory.getLogger("EventRouter")

        log.info("Received event: ${call.receiveText()}")

        // If it is a challenge, respond as required
        challengeResponse(call)?.let { return@post call.respondText { it } }

        // If it's a message then just do it concurrently
        withContext(Dispatchers.Default) {
            messageResponse(call)?.let {
                log.info("Reaction attempt received response from Slack: $it")
            }
        }

        // Slack requires that all successful *delivery* be flagged as 200
        call.respond(HttpStatusCode.OK)
    }
}
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

        // Go ahead and do this asynchronously, Slack's API mandates
        // quick responses to the webhooks, so process separately
        // and respond as quickly as possible
        withContext(Dispatchers.Default) {
            try {
                val reactionResult = call.receive<Event<Message>>()
                    .event
                    .reaction("heart")
                    .send()

                println("Reacting to message resulted with: $reactionResult")
            } catch (e: Exception) {

                // Look, it's not what one would really call kosher, but we fail here for
                // 1. Event not deserialising (aka a non-message event)
                // 2. The POST request to slack failing
                // 3. Some other miscellaneous reason
                // It's not good practice but don't @ me rn
                println("Error processing message. Error: ${e.message}")
            }
        }

        // Slack requires that all successful *delivery* be flagged as 200
        call.respond(HttpStatusCode.OK)
    }
}
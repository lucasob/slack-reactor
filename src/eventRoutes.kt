package com.lucasob

import com.lucasob.slack.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.slf4j.LoggerFactory

fun Route.eventRoutes() {
    post("/event") {

        val log = LoggerFactory.getLogger("EventRouter")

        with(call.receiveText()) {
            log.info("Received event: $this")

            getMessage(this)?.let { message ->
                if (message.isFundingMessage()) {
                    message
                        .react(reactionName())
                        .send()
                        .also { response -> log.info("Slack responded with: $response") }
                }

                return@with HttpStatusCode.OK
            }

            // If it is a challenge, respond directly to the call with the challenge
            // Do this last as it is never a challenge unless setting up the app.
            getChallenge(this)?.let { challenge ->
                return@post call.respondText { challenge }
                    .also { log.info("Received challenge from slack. Responded with (Challenge=$challenge)") }
            }
        }?.let {
            // Return the code indicated by the message processing
            return@post call.respond(it)
        }

        // If we failed to extract a message, or a challenge, still return a 2xx
        call.respond(HttpStatusCode.Accepted).also { log.info("Event was ignored") }
    }
}
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
                return@with message.react(reactionName()).send()
            }

            // If it is a challenge, respond directly to the call with the challenge
            // Do this last as it is likely *never* a challenge
            getChallenge(this)?.let { challenge ->
                log.info("Received challenge from slack. Responded with (Challenge=$challenge)")
                return@post call.respondText { challenge }
            }
        }?.let {
            // Successfully reacted to the message
            return@post call.respond(HttpStatusCode.OK)
        }

        // If we failed to extract a message, or a challenge, still return a 2xx
        call.respond(HttpStatusCode.Accepted)
    }
}
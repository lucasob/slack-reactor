package com.lucasob.slack

import io.ktor.application.*
import io.ktor.request.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
data class Challenge(val type: String, val token: String, val challenge: String)

suspend fun challengeResponse(call: ApplicationCall): String? {

    val log = LoggerFactory.getLogger("challengeResponse")

    return try {
        with(call.receive<Challenge>()) {
            log.info("Received challenge. Responding with ${this.challenge}")
            this.challenge
        }
    } catch (e: Exception) {
        null
    }
}
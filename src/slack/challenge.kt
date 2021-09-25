package com.lucasob.slack

import io.ktor.application.*
import io.ktor.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Challenge(val type: String, val token: String, val challenge: String)

suspend fun challengeResponse(call: ApplicationCall): String? {
    return try {
        with(call.receive<Challenge>()) {
            this.challenge
        }
    } catch (e: Exception) {
        null
    }
}
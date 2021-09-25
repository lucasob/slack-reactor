package com.lucasob.slack

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

@Serializable
data class Challenge(val type: String, val token: String, val challenge: String)

fun challengeResponse(requestBody: String): String? {

    val log = LoggerFactory.getLogger("challengeResponse")

    return try {
        with(Json.decodeFromString<Challenge>(requestBody)) {
            log.info("Received challenge. Responding with ${this.challenge}")
            this.challenge
        }
    } catch (e: Exception) {
        null
    }
}
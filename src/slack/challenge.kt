package com.lucasob.slack

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Challenge(val type: String, val token: String, val challenge: String)

fun getChallenge(requestBody: String): String? {

    return try {
        with(Json.decodeFromString<Challenge>(requestBody)) {
            this.challenge
        }
    } catch (e: Exception) {
        null
    }
}
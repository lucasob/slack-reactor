package com.lucasob.slack

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

@Serializable
data class Message(
    val type: String,
    val channel: String,
    val user: String,
    val text: String,
    @SerialName("client_msg_id") val clientMessageId: String,
    @SerialName("ts") val timestamp: String,
    @SerialName("event_ts") val eventTimestamp: String,
    @SerialName("channel_type") val channelType: String
)

/**
 * Given an event, generate a reaction
 *
 * @param name name of the reaction within the Slack channel
 */
fun Message.reaction(name: String) =
    Reaction(channel = this.channel, name = name, timestamp = this.timestamp)

suspend fun messageResponse(requestBody: String): Response? {

    val log = LoggerFactory.getLogger("messageResponse")

    return try {
        log.info("Trying to parse message received")
        with(Json { ignoreUnknownKeys = true; coerceInputValues = true }.decodeFromString<Event<Message>>(requestBody)) {
            log.info("Received Message: ${this.event}")
            this.event.reaction("heart").send()
        }
    } catch (e: Exception) {
        null
    }
}
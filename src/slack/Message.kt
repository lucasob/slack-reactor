package com.lucasob.slack

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun reactionName() = System.getenv("SLACK_REACTION_TYPE") ?: "rocket"

@Serializable
data class Message(
    val type: String,
    val subtype: String? = null,
    val channel: String,
    val user: String? = null,
    val text: String,
    @SerialName("client_msg_id") val clientMessageId: String? = null,
    @SerialName("ts") val timestamp: String,
    @SerialName("event_ts") val eventTimestamp: String,
    @SerialName("channel_type") val channelType: String
)

/**
 * Given an event, generate a reaction
 *
 * @param name name of the reaction within the Slack channel
 */
fun Message.react(name: String) =
    Reaction(channel = this.channel, name = name, timestamp = this.timestamp)

fun Message.isFundingMessage() =
    this.text.lowercase().contains("funded")

fun getMessage(json: String): Message? {
    return try {
        with(Json { ignoreUnknownKeys = true; coerceInputValues = true }.decodeFromString<Event<Message>>(json)) {
            this.event
        }
    } catch (e: Exception) {
        null
    }
}

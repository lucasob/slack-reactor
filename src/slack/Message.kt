package com.lucasob.slack

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val type: String,
    val channel: String,
    val user: String,
    val text: String,
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
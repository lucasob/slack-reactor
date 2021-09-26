package com.lucasob.slack

import java.time.Instant

fun randomString(size: Int) = List(size) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
}.joinToString("")

fun generateMessage() = Message(
    type = "message",
    subtype = "bot_message",
    timestamp = Instant.now().toString(),
    text = randomString(16),
    channel = randomString(8),
    channelType = "channel",
    eventTimestamp = Instant.now().toString()
)

fun generateEvent() = Event(
    event = generateMessage(),
    token = randomString(32),
    teamId = randomString(8),
    apiAppId = randomString(8),
    type = "event_callback",
    eventTime = Instant.now().toEpochMilli(),
    eventId = randomString(8),
    authedTeams = listOf(),
    eventContext = randomString(32),
    isExtSharedChannel = false
)
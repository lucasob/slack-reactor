package com.lucasob.slack

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Event<T>(
    // NOTE - To support ordered destructuring, event must be at the top
    // such that it can be referenced first when destructuring an Event
    val event: T,
    val token: String,
    @SerialName("team_id") val teamId: String,
    @SerialName("api_app_id") val apiAppId: String,
    val type: String,
    @SerialName("authed_teams") val authedTeams: List<String>,
    @SerialName("event_id") val eventId: String,
    @SerialName("event_time") val eventTime: Long
)
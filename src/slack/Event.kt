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
    @SerialName("event_id") val eventId: String,
    @SerialName("event_time") val eventTime: Long,
    @SerialName("authorizations") val authedTeams: List<Authorisation>,
    @SerialName("is_ext_shared_channel") val isExtSharedChannel: Boolean,
    @SerialName("event_context") val eventContext: String
)

@Serializable
data class Authorisation(
    @SerialName("enterprise_id") val enterpriseId: String?,
    @SerialName("team_id") val teamId: String,
    @SerialName("user_id") val userId: String,
    @SerialName("is_bot") val isBot: Boolean,
    @SerialName("is_enterprise_install") val isEnterpriseInstall: Boolean
)
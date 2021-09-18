package com.lucasob.slack

import kotlinx.serialization.Serializable

@Serializable
data class Response(val ok: Boolean, val error: String? = null)
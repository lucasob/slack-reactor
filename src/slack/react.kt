package com.lucasob.slack

data class Reaction(val channel: String, val name: String, val timestamp: String) {

    companion object {
        const val targetUrl = "reactions.add"
    }

}

/**
 * Send the reaction back to Slack
 */
suspend fun Reaction.send(): Response {
    return post(
        target = Reaction.targetUrl,
        parameters = mapOf("channel" to this.channel, "name" to this.name, "timestamp" to this.timestamp)
    )
}
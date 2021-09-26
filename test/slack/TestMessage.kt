package com.lucasob.slack

import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class TestMessage {

    @Test
    fun testCanReceiveMessage() {
        val randomEvent: Event<Message> = generateEvent()
        getMessage(Json.encodeToString(randomEvent)) shouldBe randomEvent.event
    }

    @Test
    fun testCanReact() {
        table(
            headers("Reaction Environment Variable", "Reaction Name"),
            row("fingerguns", "fingerguns"),
            row("heart", "heart"),
            row(null, "rocket")
        ).forAll { reaction, name ->
            withEnvironment("SLACK_REACTION_TYPE", reaction) {
                with(generateMessage().react(reactionName())) {
                    this.name shouldBe name
                }
            }
        }
    }
}
package com.lucasob

import com.lucasob.slack.Challenge
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class RouteTest {

    @Test
    fun testCanProcessChallengeRequest() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/event") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(Challenge(type="url_verification", token="some token", challenge = "hello!")))
            }.apply {
                response.status() shouldBe HttpStatusCode.OK
                response.content shouldBe "hello!"
            }
        }
    }
}
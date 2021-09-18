package com.lucasob.slack

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlin.system.exitProcess
import io.ktor.client.request.*
import io.ktor.http.*

fun slackBaseUrl(): String {
    val url = System.getenv("SLACK_API_URL")

    if (url.isEmpty()) {
        exitProcess(1)
    }

    return url
}

fun authToken(): String {
    val token = System.getenv("SLACK_AUTH_TOKEN")

    if (token.isEmpty()) {
        exitProcess(1)
    }

    return token
}

fun client(): HttpClient {
    return HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = false
                ignoreUnknownKeys = true
            })
        }
    }
}

/**
 * Performs a HTTP POST to slack
 *
 * @param target    The target URL of the POST request. The base URL is included in the post function.
 *                  The base url will have a suffixed slash already
 * @param parameters Query parameters for the request
 */
suspend inline fun <reified R> post(target: String, parameters: Map<String, String>): R {

    val client = client()

    val response = client.post<R>("${slackBaseUrl()}/${target}") {
        headers {
            append(HttpHeaders.Authorization, "Bearer ${authToken()}")
        }
        parameters.forEach { p -> parameter(p.key, p.value) }
    }

    client.close()
    return response
}
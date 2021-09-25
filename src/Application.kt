package com.lucasob

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        anyHost()
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            encodeDefaults = false
            ignoreUnknownKeys = true
        })
    }

    // Receive a call multiple times
    install(DoubleReceive)

    routing {

        // Generic aliveness check
        get("/") {
            call.respondText("Howzit", contentType = ContentType.Text.Plain)
        }

        // Slack will POST all events. Capture these
        eventRoutes()
    }
}


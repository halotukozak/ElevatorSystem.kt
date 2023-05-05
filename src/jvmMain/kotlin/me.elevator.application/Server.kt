package me.elevator.application

import Elevator
import http.InitRequest
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::myApplicationModule).start(wait = true)
}

fun Application.myApplicationModule() {

    var elevatorSystem: ElevatorSystem? = null

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    install(Compression) {
        gzip()
    }

    routing {
        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("index.html")!!.readText(),
                ContentType.Text.Html
            )
        }
        get(Elevator.path) {
            call.respond(elevatorSystem?.status() ?: call.respond(HttpStatusCode.NotFound, "Initialize system first"))
        }
        post(Elevator.initPath) {
            val request = call.receive<InitRequest>()
            if (elevatorSystem != null) call.respond(HttpStatusCode.BadRequest, "System is already initialized")
            elevatorSystem = ElevatorSystem(request.numberOfElevators, request.numberOfLevels)
            call.respond(HttpStatusCode.Accepted)
        }
        static("/") {
            resources("")
        }

    }
}
package me.elevator.application

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
import io.ktor.util.pipeline.*
import model.Elevator
import model.Pickup


fun main() {
    embeddedServer(
        Netty,
        port = 8000,
        module = Application::myApplicationModule
    ).start(wait = true)
}

fun Application.myApplicationModule() {

    var elevatorSystem: ElevatorSystem? = null

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)

//        allowHost(System.getenv("HOST"), schemes = listOf("http", "https"))
    }

    install(Compression) { gzip() }

    routing {
        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("index.html")!!.readText(),
                ContentType.Text.Html
            )
        }

        delete("/reset") {
            elevatorSystem = null
            call.respond(HttpStatusCode.NoContent)
        }

        get(Elevator.path) {
            call.respond(elevatorSystem?.status() ?: notInitializedError())
        }

        post(Elevator.initPath) {
            val request = call.receive<InitRequest>()
            if (elevatorSystem != null) notInitializedError()
            elevatorSystem = ElevatorSystem(request.numberOfElevators)
            call.respond(HttpStatusCode.OK)
        }

        post(Elevator.pickupPath) {
            val request = call.receive<Pickup>()
            elevatorSystem?.let {
                if (it.pickup(request)) call.respond(HttpStatusCode.Accepted)
                else call.respond(HttpStatusCode.NotAcceptable)
            } ?: notInitializedError()
        }

        post("/step") {
            elevatorSystem?.makeStep() ?: notInitializedError()
            call.respond(HttpStatusCode.OK)
        }

        static("/") {
            resources("")
        }

    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.notInitializedError() {
    call.respond(HttpStatusCode.BadRequest, "System is already initialized")
}
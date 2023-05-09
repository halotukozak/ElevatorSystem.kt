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
        port = System.getenv("PORT")?.toInt() ?: 8000,
        module = Application::myApplicationModule
    ).start(wait = true)
}


fun Application.myApplicationModule() {

    val elevatorSystems = ElevatorSystemStorage()

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader("user-id")

        anyHost()
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
            elevatorSystems.removeElevatorSystem(userSession())
            call.respond(HttpStatusCode.NoContent)
        }

        get(Elevator.path) {
            val elevatorSystem = elevatorSystems.getElevatorSystem(userSession())
            call.respond(elevatorSystem?.status() ?: notInitializedError())
        }

        post(Elevator.initPath) {
            val request = call.receive<InitRequest>()
            elevatorSystems.addElevatorSystem(request.numberOfElevators, userSession())
            call.respond(HttpStatusCode.OK)
        }

        post(Elevator.pickupPath) {
            val request = call.receive<Pickup>()
            val elevatorSystem = elevatorSystems.getElevatorSystem(userSession())
            elevatorSystem?.let {
                if (it.pickup(request)) call.respond(HttpStatusCode.Accepted)
                else call.respond(HttpStatusCode.NotAcceptable)
            } ?: notInitializedError()
        }

        post("/step") {
            val elevatorSystem = elevatorSystems.getElevatorSystem(userSession())
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

suspend fun PipelineContext<*, ApplicationCall>.userSession(): String = call.request.headers["user-id"] ?: ""

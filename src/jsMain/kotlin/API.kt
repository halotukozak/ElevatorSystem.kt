import components.errorContext
import http.InitRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import model.Elevator
import model.ElevatorStatus
import model.Passenger
import model.Pickup
import react.useContext
import web.location.location

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

val serverUrl = location.pathname

suspend inline fun <reified T> get(path: String): T {
    val response = client.get(serverUrl + path)
    return if (response.status.isSuccess()) {
        response.body() as T
    } else {
        useContext(errorContext).addLast(response.status.description)
        error(response)
    }
}

suspend fun post(path: String, body: Any? = null) = try {
    client.post(serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
} catch (ex: ResponseException) {
    useContext(errorContext).addLast(ex.message ?: "Unknown error")
}

suspend fun delete(path: String, body: Any? = null) = try {
    client.delete(serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
} catch (ex: ResponseException) {
    useContext(errorContext).addLast(ex.message ?: "Unknown error")
}


suspend fun init(numberOfElevators: Int, numberOfFloors: Int) {
    post(Elevator.initPath, InitRequest(numberOfElevators, numberOfFloors))
}

suspend fun getStatus(): List<ElevatorStatus> = get(Elevator.path)
suspend fun reset() = delete("/reset")
suspend fun pickup(floor: Int, destination: Int, direction: Direction) {
    post(Elevator.pickupPath, Pickup(Passenger(floor, destination), floor, direction))
}

suspend fun step() {
    post("/step")
}
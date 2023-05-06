import Components.errorContext
import http.InitRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import model.Elevator
import react.useContext

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend inline fun <reified T> get(path: String): T {
    val response = client.get(Config.serverUrl + path)
    return if (response.status.isSuccess()) {
        response.body() as T
    } else {
        useContext(errorContext).addLast(response.status.description)
        error(response)
    }
}

suspend fun post(path: String, body: Any? = null) = try {
    client.post(Config.serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
} catch (ex: ResponseException) {
    useContext(errorContext).addLast(ex.message ?: "Unknown error")
}

suspend fun delete(path: String, body: Any? = null) = try {
    client.delete(Config.serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }
} catch (ex: ResponseException) {
    useContext(errorContext).addLast(ex.message ?: "Unknown error")
}


suspend fun init(numberOfElevators: Int, numberOfFloors: Int) {
    post(Elevator.initPath, InitRequest(numberOfElevators, numberOfFloors))
}

suspend fun getElevators(): List<Elevator> = get(Elevator.path)
suspend fun reset() = delete("/reset")
suspend fun pickup(elevator: Elevator, level: Int, direction: Direction) {
    post(Elevator.initPath, mapOf("elevator" to elevator, "level" to level, "direction" to direction))
}
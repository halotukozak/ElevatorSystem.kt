import com.benasher44.uuid.uuid4
import http.InitRequest
import http.StatusResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import model.Elevator
import model.Passenger
import model.Pickup
import web.location.location

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

val serverUrl = "${location.protocol}//${location.host}"
val userId = uuid4().toString()

suspend inline fun <reified T> get(path: String): T = try {
    val response = client.get(serverUrl + path) {
        headers {
            append("user-id", userId)
        }
    }
    if (!response.status.isSuccess()) throw IllegalStateException(response.status.description)
    response.body() as T
} catch (ex: Exception) {
    error(ex.message ?: "Unknown error")
}

suspend fun post(path: String, body: Any? = null) = try {
    client.post(serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
        headers {
            append("user-id", userId)
        }
    }
} catch (ex: Exception) {
    error(ex.message ?: "Unknown error")
}

suspend fun delete(path: String, body: Any? = null) = try {
    client.delete(serverUrl + path) {
        contentType(ContentType.Application.Json)
        setBody(body)
        headers {
            append("user-id", userId)
        }
    }
} catch (ex: Exception) {
    error(ex.message ?: "Unknown error")
}


suspend fun init(numberOfElevators: Int, numberOfFloors: Int) {
    post(Elevator.initPath, InitRequest(numberOfElevators, numberOfFloors))
}

suspend fun getStatus(): StatusResponse = get(Elevator.path)
suspend fun reset() = delete("/reset")

suspend fun enableDormitoryMode() = post("/dormitoryMode")


suspend fun pickup(floor: Int, destination: Int, direction: Direction) =
    post(Elevator.pickupPath, Pickup(Passenger(floor, destination), floor, direction))

suspend fun step() {
    post("/step")
}
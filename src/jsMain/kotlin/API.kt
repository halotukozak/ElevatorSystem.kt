import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun init(numberOfElevators: Int, numberOfFloors: Int) {
    jsonClient.post(Elevator.initPath) {
        contentType(ContentType.Application.Json)
        setBody(mapOf("numberOfElevators" to numberOfElevators, "numberOfFloors" to numberOfFloors))
    }
}

suspend fun getElevators(): List<Elevator> {
    return jsonClient.get(Elevator.path).body()
}

suspend fun pickup(elevator: Elevator, level: Int, direction: Direction) {
    jsonClient.post(Elevator.path) {
        contentType(ContentType.Application.Json)
        setBody(mapOf("elevator" to elevator, "level" to level, "direction" to direction))
    }
}
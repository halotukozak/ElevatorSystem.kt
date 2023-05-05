import http.InitRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

val jsonClient = HttpClient {
    expectSuccess=true
    install(ContentNegotiation) {
        json()
    }
}

suspend inline fun <reified T> get(path: String): T {
    try{
        val response = jsonClient.get(path)
        return response.body() as T
    }catch (ex: ResponseException){
        TODO()
    }
}

suspend fun init(numberOfElevators: Int, numberOfFloors: Int) {
    val response = jsonClient.post(Elevator.initPath) {
        contentType(ContentType.Application.Json)
        setBody(InitRequest(numberOfElevators, numberOfFloors))
    }
    if (response.status.isSuccess()) return response.body()
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
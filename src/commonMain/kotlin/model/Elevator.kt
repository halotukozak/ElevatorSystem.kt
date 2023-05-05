import kotlinx.serialization.Serializable


@Serializable
class Elevator {
    val id: Int = lastId++

    companion object {
        const val path = "/elevators"
        const val initPath = "$path/init"
        var lastId = 1
    }
}
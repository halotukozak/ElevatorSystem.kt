import kotlinx.serialization.Serializable


@Serializable
data class Elevator(val id: Int) {
    companion object {
        const val path = "/elevators"
        const val initPath = "$path/init"

    }
}
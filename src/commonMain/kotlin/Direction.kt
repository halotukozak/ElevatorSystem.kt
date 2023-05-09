import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
enum class Direction {
    UP,
    DOWN;

    companion object {
        fun random(): Direction = if (Random.nextInt(0, 1) == 0) UP else DOWN
    }
}

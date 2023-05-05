package http

import kotlinx.serialization.Serializable

@Serializable
data class InitRequest(val numberOfElevators: Int, val numberOfLevels: Int)

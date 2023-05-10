package model

import kotlinx.serialization.Serializable

@Serializable
data class ElevatorStatus(
    val id: Int,
    val currentLevel: Int,
    val passengers: Collection<Passenger>,
    val isBroken: Boolean
)
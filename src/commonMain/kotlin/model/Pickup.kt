package model

import Direction
import kotlinx.serialization.Serializable

@Serializable
data class Pickup(val passenger: Passenger, val currentFloor: Int, val direction: Direction)
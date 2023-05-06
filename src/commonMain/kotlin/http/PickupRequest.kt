package http

import kotlinx.serialization.Serializable
import model.Passenger
import model.Pickup

@Serializable
data class PickupRequest(val pickups: List<Pickup>)

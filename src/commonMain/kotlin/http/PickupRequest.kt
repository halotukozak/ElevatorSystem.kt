package http

import kotlinx.serialization.Serializable
import model.Pickup

@Serializable
data class PickupRequest(val pickups: List<Pickup>)

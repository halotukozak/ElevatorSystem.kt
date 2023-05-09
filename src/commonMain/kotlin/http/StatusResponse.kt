package http

import kotlinx.serialization.Serializable
import model.ElevatorStatus

@Serializable
data class StatusResponse(val elevatorsStatus: List<ElevatorStatus>, val waitingPassengers: Map<Int, Int>)

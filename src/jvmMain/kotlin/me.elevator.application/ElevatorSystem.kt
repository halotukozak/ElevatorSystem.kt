package me.elevator.application

import http.StatusResponse
import model.Elevator
import model.Pickup
import java.util.*

class ElevatorSystem(numberOfElevators: Int) {

    private var elevators: List<Elevator>
    private val waitingPickups = LinkedList<Pickup>()

    init {
        this.elevators = List(numberOfElevators) { Elevator() }
    }

    fun pickup(pickup: Pickup) {
        waitingPickups.add(pickup)
    }

    fun status(): StatusResponse= StatusResponse(elevators.map { it.status() },
        waitingPickups.groupingBy { it.passenger.startingFloor }.eachCount())

    fun makeStep() {
        val freeElevators = elevators.filter { it.canPickup() }.toMutableList()
        while (freeElevators.isNotEmpty() && waitingPickups.isNotEmpty()) {
            val pickup = waitingPickups.poll()
            val elevator = freeElevators.minBy { it.calcDistance(pickup.currentFloor, pickup.direction) }

            elevator.pickup(passenger = pickup.passenger)
            if (!elevator.canPickup()) freeElevators.remove(elevator)
        }
        elevators.forEach { it.makeStep() }
    }


}

class ElevatorSystemStorage {
    private val elevatorSystems = mutableMapOf<String, ElevatorSystem>()
    fun getElevatorSystem(user: String) = elevatorSystems[user]
    fun removeElevatorSystem(user: String) = elevatorSystems.remove(user)
    fun addElevatorSystem(numberOfElevators: Int, userSession: String) {
        elevatorSystems[userSession] = ElevatorSystem((numberOfElevators))
    }

}
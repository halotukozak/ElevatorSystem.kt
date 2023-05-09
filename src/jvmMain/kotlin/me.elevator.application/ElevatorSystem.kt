package me.elevator.application

import model.Elevator
import model.ElevatorStatus
import model.Pickup

class ElevatorSystem(numberOfElevators: Int) {

    private var elevators: List<Elevator>

    fun pickup(pickup: Pickup): Boolean {
        val freeElevators = elevators.filter { it.canPickup() }
        val elevator = freeElevators.minByOrNull { it.calcDistance(pickup.currentFloor, pickup.direction) }
            ?: freeElevators.firstOrNull() ?: elevators.minByOrNull {
                it.calcDistance(
                    pickup.currentFloor,
                    pickup.direction
                )
            }
        elevator?.pickup(passenger = pickup.passenger) ?: return false
        return true
    }

    fun status(): List<ElevatorStatus> = elevators.map { it.status() }

    fun makeStep() {
        elevators.forEach { it.makeStep() }
    }

    init {
        this.elevators = List(numberOfElevators) { Elevator() }
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
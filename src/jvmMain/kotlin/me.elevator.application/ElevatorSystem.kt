package me.elevator.application

import model.Elevator
import model.ElevatorStatus
import model.Pickup

class ElevatorSystem(numberOfElevators: Int, numberOfFloors: Int) {
    private var elevators: List<Elevator> = List(numberOfElevators) { Elevator() }

    val numberOfLevels: Int = 0
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
}
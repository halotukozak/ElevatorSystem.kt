package me.elevator.application

import Direction
import model.Elevator
import model.ElevatorStatus

class ElevatorSystem(numberOfElevators: Int, numberOfLevels: Int) {
    private var elevators: List<Elevator> = List(numberOfElevators) { Elevator() }

    val numberOfLevels: Int = 0
    fun pickup(level: Int, direction: Direction) {}
    fun status(): List<ElevatorStatus> = elevators.map { it.status() }

    fun getAllElevators() = elevators


}
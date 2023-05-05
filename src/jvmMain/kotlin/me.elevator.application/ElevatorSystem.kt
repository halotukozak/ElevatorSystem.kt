package me.elevator.application

import Direction
import Elevator
import model.ElevatorStatus

class ElevatorSystem(numberOfElevators: Int, numberOfLevels: Int) {
    private var elevators: Set<Elevator> = buildSet(numberOfElevators) { Elevator() }

    val numberOfLevels: Int = 0
    fun pickup(level: Int, direction: Direction) {}
    fun status(): List<ElevatorStatus> {
        TODO()
    }


}
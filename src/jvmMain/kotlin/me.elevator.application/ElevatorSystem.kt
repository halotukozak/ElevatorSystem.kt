package me.elevator.application

import http.StatusResponse
import model.Elevator
import model.Pickup
import java.util.*
import kotlin.random.Random

class ElevatorSystem(numberOfElevators: Int) {

    private var elevators: List<Elevator>
    private val waitingPickups = LinkedList<Pickup>()

    private var dormitoryModeEnabled = false

    init {
        this.elevators = List(numberOfElevators) { Elevator() }
    }

    fun pickup(pickup: Pickup) {
        waitingPickups.add(pickup)
    }

    fun status(): StatusResponse {
        val waitingPassengers = waitingPickups.groupingBy { it.passenger.startingFloor }.eachCount().toMutableMap()

        elevators.flatMap { it.passengers() }.filter { it.isWaiting }.groupingBy { it.startingFloor }.eachCount()
            .forEach { (floor, count) -> waitingPassengers[floor] = waitingPassengers.getOrDefault(floor, 0) + count }

        return StatusResponse(
            elevators.map { it.status() },
            waitingPassengers
        )
    }


    private fun drawAndDo(f: Elevator.() -> Unit) {
        if (Random.nextInt(0, 10) == 1) elevators[Random.nextInt(0, elevators.size)].f()
    }

    private fun breakRandomElevator() = drawAndDo(Elevator::makeBroken)
    private fun repairRandomElevator() = drawAndDo(Elevator::repair)


    fun makeStep() {
        if (dormitoryModeEnabled) {
            repairRandomElevator()
            breakRandomElevator()
        }

        val freeElevators = elevators.filter { it.canPickup() }.toMutableList()
        while (freeElevators.isNotEmpty() && waitingPickups.isNotEmpty()) {
            val pickup = waitingPickups.poll()
            val elevator = freeElevators.minBy { it.calcDistance(pickup.currentFloor, pickup.direction) }

            elevator.pickup(passenger = pickup.passenger)
            if (!elevator.canPickup()) freeElevators.remove(elevator)
        }
        elevators.forEach { it.makeStep() }
    }

    fun enableDormitoryMode() {
        dormitoryModeEnabled = true
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
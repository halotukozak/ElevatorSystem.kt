package model

import Config
import Direction
import Direction.DOWN
import Direction.UP
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import utils.ElevatorDequePQ
import kotlin.math.abs


@Serializable
data class Elevator(private var currentFloor: Int = 0) {
    val id: Int = lastId++

    @Transient
    private val passengers = ElevatorDequePQ()
    private var direction: Direction? = null


    companion object {
        const val path = "/elevators"
        const val initPath = "$path/init"
        const val pickupPath = "$path/pickup"
        var lastId = 1
    }

    fun status(): ElevatorStatus = ElevatorStatus(id, currentFloor, passengers.all())
    fun canPickup() = passengers.size() < Config.maxElevatorSize

    fun pickup(passenger: Passenger) {
        passengers.pickUp(passenger)
        if (direction == null)
            direction = when {
                passenger.startingFloor > currentFloor -> UP
                passenger.startingFloor < currentFloor -> DOWN
                else -> when {
                    passenger.destination > currentFloor -> UP
                    passenger.destination < currentFloor -> DOWN
                    else -> null
                }
            }
    }

    fun makeStep() {
        passengers.changeState(currentFloor)

        when (direction) {
            DOWN -> {
                currentFloor--
                passengers.dropOff(currentFloor)

                val minFloor = passengers.getMin()
                minFloor?.let { if (it >= currentFloor) changeDirection() } ?: stop()
            }

            UP -> {
                currentFloor++
                passengers.dropOff(currentFloor)

                val maxFloor = passengers.getMax()
                maxFloor?.let { if (it <= currentFloor) changeDirection() } ?: stop()
            }

            null -> {
                passengers.dropOff(currentFloor)
            }
        }
    }

    private fun changeDirection() {
        when (direction) {
            DOWN -> direction = UP
            UP -> direction = DOWN
            null -> {}
        }
    }

    private fun stop() {
        direction = null
    }

    //    The Elevator metrics, which doesn't fill metrics conditions
    fun calcDistance(passengerFloor: Int, passengerDirection: Direction): Int {
        val minFloor = passengers.getAbsoluteMin() ?: -1
        val maxFloor = passengers.getAbsoluteMax() ?: -1

        val minToMax = abs(maxFloor - minFloor)
        return when (this.direction) {
            UP -> when (passengerDirection) {
                UP ->
                    if (passengerFloor >= currentFloor) abs(passengerFloor - currentFloor)
                    else abs(maxFloor - currentFloor) + minToMax + passengerFloor

                DOWN -> abs(maxFloor - currentFloor) + abs(passengerFloor - maxFloor)

            }


            DOWN -> {
                when (passengerDirection) {
                    UP -> abs(minFloor - currentFloor) + abs(passengerFloor - minFloor)

                    DOWN ->
                        if (passengerFloor > currentFloor) abs(minFloor - currentFloor) + minToMax + (maxFloor - passengerFloor)
                        else abs(passengerFloor - currentFloor)
                }

            }

            null -> abs(passengerFloor - currentFloor)

        }
    }
}



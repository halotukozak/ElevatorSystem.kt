package utils

import model.Passenger
import kotlin.math.max
import kotlin.math.min

class ElevatorDequePQ {
    private val s = HashSet<Passenger>()

    fun size(): Int = s.size
    fun isEmpty(): Boolean = s.isEmpty()

    fun pickUp(x: Passenger) {
        s.add(x)
    }

    fun getMin(): Int? =
        s.minOfOrNull { if (it.isWaiting) it.startingFloor else it.destination }

    fun getMax(): Int? =
        s.maxOfOrNull { if (it.isWaiting) it.startingFloor else it.destination }


    fun getAbsoluteMin(): Int? =
        s.minOfOrNull { min(it.startingFloor, it.destination) }

    fun getAbsoluteMax(): Int? =
        s.maxOfOrNull { max(it.startingFloor, it.destination) }

    fun dropOff(floor: Int) {
        s.removeAll { !it.isWaiting && it.destination == floor }
    }

    fun changeState(currentFloor: Int) {
        s.forEach { if (it.startingFloor == currentFloor) it.isWaiting = false }
    }

    fun all() = s


}
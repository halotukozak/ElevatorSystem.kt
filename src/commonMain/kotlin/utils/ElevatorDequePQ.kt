package utils

import model.Passenger

class ElevatorDequePQ {
    private val s = HashSet<Passenger>()

    fun size(): Int = s.size
    fun isEmpty(): Boolean = s.isEmpty()

    fun pickUp(x: Passenger) {
        s.add(x)
    }

    fun peekMinFloor(): Int? = s.minByOrNull { it.destination }?.destination

    fun peekMaxFloor(): Int? = s.maxByOrNull { it.destination }?.destination

    fun dropOff(floor: Int) {
        s.removeAll { !it.isWaiting && it.destination == floor }
    }

    fun changeState(currentFloor: Int) {
        s.forEach { if (it.startingFloor == currentFloor) it.isWaiting = false }
    }

    fun all() = s


}
package model

import Direction
import kotlin.test.Test
import kotlin.test.assertEquals

class ElevatorTest {
    private val groundFloor = 0
    private val lastFloor = 10

    private val passengerWithFloors = (groundFloor..lastFloor).map { index -> Passenger(-1, index) }

    private fun EmptyElevator(floor: Int): Elevator = Elevator(floor)
    private fun UpElevator(floor: Int): Elevator {
        val elevator = Elevator(floor)
        if (floor != lastFloor) elevator.pickup(passengerWithFloors[lastFloor])
        if (floor != groundFloor) elevator.pickup(passengerWithFloors[groundFloor])
        return elevator
    }

    private fun DownElevator(floor: Int): Elevator {
        val elevator = Elevator(floor)
        if (floor != groundFloor) elevator.pickup(passengerWithFloors[groundFloor])
        if (floor != lastFloor) elevator.pickup(passengerWithFloors[lastFloor])
        return elevator
    }

    @Test
    fun elevatorUpPassUp() {
        val passDirection = Direction.UP
        assertEquals(0, UpElevator(groundFloor).calcDistance(groundFloor, passDirection))
        assertEquals(2, UpElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(18, UpElevator(2).calcDistance(groundFloor, passDirection))
        assertEquals(3, UpElevator(2).calcDistance(5, passDirection))
        assertEquals(0, UpElevator(5).calcDistance(5, passDirection))

    }

    @Test
    fun elevatorDownPassUp() {
        val passDirection = Direction.UP
        assertEquals(2, DownElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(2, DownElevator(2).calcDistance(groundFloor, passDirection))
        assertEquals(6, DownElevator(5).calcDistance(1, passDirection))
        assertEquals(10, DownElevator(5).calcDistance(5, passDirection))
    }

    @Test
    fun elevatorNullPassUp() {
        val passDirection = Direction.UP
        assertEquals(0, EmptyElevator(groundFloor).calcDistance(groundFloor, passDirection))
        assertEquals(2, EmptyElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(2, EmptyElevator(2).calcDistance(groundFloor, passDirection))
    }

    @Test
    fun elevatorUpPassDown() {
        val passDirection = Direction.DOWN
        assertEquals(20, UpElevator(groundFloor).calcDistance(groundFloor, passDirection))
        assertEquals(18, UpElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(18, UpElevator(2).calcDistance(groundFloor, passDirection))
        assertEquals(13, UpElevator(5).calcDistance(2, passDirection))
        assertEquals(13, UpElevator(2).calcDistance(5, passDirection))
        assertEquals(10, UpElevator(5).calcDistance(5, passDirection))
        assertEquals(0, UpElevator(lastFloor).calcDistance(lastFloor, passDirection))
    }

    @Test
    fun elevatorDownPassDown() {
        val passDirection = Direction.DOWN
        assertEquals(18, DownElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(2, DownElevator(2).calcDistance(groundFloor, passDirection))
        assertEquals(3, DownElevator(5).calcDistance(2, passDirection))
        assertEquals(17, DownElevator(2).calcDistance(5, passDirection))
        assertEquals(0, DownElevator(5).calcDistance(5, passDirection))
        assertEquals(0, DownElevator(lastFloor).calcDistance(lastFloor, passDirection))
    }

    @Test
    fun elevatorNullPassDown() {
        val passDirection = Direction.DOWN
        assertEquals(0, EmptyElevator(groundFloor).calcDistance(groundFloor, passDirection))
        assertEquals(2, EmptyElevator(groundFloor).calcDistance(2, passDirection))
        assertEquals(2, EmptyElevator(2).calcDistance(groundFloor, passDirection))
    }


}
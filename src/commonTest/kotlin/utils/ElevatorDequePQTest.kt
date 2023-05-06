package utils

import model.Passenger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ElevatorDequePQTest {


    @Test
    fun size() {
        assertEquals(0, ElevatorDequePQ().size())
        val tmp = ElevatorDequePQ()
        tmp.pickUp(Passenger(0, 1))
        assertEquals(1, tmp.size())
    }

    @Test
    fun isEmpty() {
        assertTrue( ElevatorDequePQ().isEmpty())
        val tmp = ElevatorDequePQ()
        tmp.pickUp(Passenger(0, 1))
        assertFalse( tmp.isEmpty())
    }

    @Test
    fun peekMinFloor() {
        assertEquals(null, ElevatorDequePQ().peekMinFloor())
        val tmp = ElevatorDequePQ()
        tmp.pickUp(Passenger(0, 1))
        assertEquals(1, tmp.peekMinFloor())
        tmp.pickUp(Passenger(0, 1))
        assertEquals(1, tmp.peekMinFloor())
        tmp.pickUp(Passenger(0, 0))
        assertEquals(0, tmp.peekMinFloor())
    }

    @Test
    fun peekMaxFloor() {
        assertEquals(null, ElevatorDequePQ().peekMaxFloor())
        val tmp = ElevatorDequePQ()
        tmp.pickUp(Passenger(0, 1))
        assertEquals(1, tmp.peekMaxFloor())
        tmp.pickUp(Passenger(0, 1))
        assertEquals(1, tmp.peekMaxFloor())
        tmp.pickUp(Passenger(0, 0))
        assertEquals(1, tmp.peekMaxFloor())
    }

    @Test
    fun dropOff() {
        val passenger0 = Passenger(0, 0)
        passenger0.isWaiting = false
        val passenger1 = Passenger(0, 1)
        passenger1.isWaiting = false


        val tmp = ElevatorDequePQ()
        tmp.dropOff(1)
        assertTrue(tmp.isEmpty())
        tmp.pickUp(passenger0)
        tmp.dropOff(5)
        assertFalse( tmp.isEmpty())
        tmp.dropOff(0)
        assertTrue( tmp.isEmpty())
        tmp.pickUp(passenger0)
        tmp.pickUp(passenger1)
        tmp.dropOff(0)
        assertFalse( tmp.isEmpty())


    }


}
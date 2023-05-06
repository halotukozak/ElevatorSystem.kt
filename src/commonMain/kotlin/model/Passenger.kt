package model

import kotlinx.serialization.Serializable

@Serializable
data class Passenger(val startingFloor: Int, val destination: Int) {
    val id: Int = lastId++
    var isWaiting = true

    companion object {
        var lastId = 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Passenger) return false
        val o = other as Passenger
        return this.id == o.id
    }

    override fun hashCode(): Int {
        var result = startingFloor
        result = 31 * result + destination
        result = 31 * result + id
        result = 31 * result + isWaiting.hashCode()
        return result
    }
}

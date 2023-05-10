# Elevator System

Elevator System is the Kotlin Multiplatform app of the three modules:
- JS (Ktor Client, Kotlin wrappers for JS, emotion, Ring UI)
- JVM (Ktor Server)
- common (Kotlin Serialization, Kotlin Coroutines)

## How to

- Go for [Elevator System deployed on Heroku](https://elevator-system.herokuapp.com).
- Choose number of floors and elevators
- Call an elevator at any floor and pick the destination or click on floor heading and send random request
- Each press of the **step** button updates the simulation by one unit of time (elevators can make one move)
- The number of asterisks in an elevator or on a floor indicates how many passengers are there.
- You can enable **dormitory mode**, setting minimum 10 waiting people on a floor. In this mode, elevators can break down, what is indicated by *service* signal
- Click on **reset** button to reset simulation

## Installation

Download and extract package from _main_ branch and run application
```sh
./gradlew run
```
Default server address is
```
http://0.0.0.0:8080
```

## Explanation of web operations
### Front-end
> The front-end site is Kotlin/JS module built with [Koltin wrappers](https://github.com/JetBrains/kotlin-wrappers) which are responsible for visualization. API Requestes are made with [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)

### Back-end
> [Ktor Server](https://ktor.io/docs/intellij-idea.html) resolves API request. A client sends user-id header that allows to identify user, because each user has its own Elevator System

## Elevator System algorithm
### Pickup
When the user sends a pickup request (current floor and direction), the passenger is queued.
### Step
Firstly, the system selects only working elevators and not full ones and assigns to them waiting passengers according to the queue order. The elevator is chosen by _elevator metrics_*: distance is contingent on the passenger's starting floor and direction, and also on the elevator's current floor, direction, maximum/minimum destination floor. If the directions are equal, it's better. Else, the elevator has to go to the extreme floor and come back, what is calculated.
If the elevator drops off all passengers at the current floor, it changes the direction or stops.
### Dormitory mode
When the user sets minimum 10 waiting people on a floor, the system starts _dormitory mode_, during which before a step it is optionally picked an empty elevator to stop working. Also, it is picked the one to be repaired. The broken elevators are significant problems in my dormitory, so I want to pay tribute to everyone, who clumps upstairs.

*Actually, I've invented the elevator metrics, which fills only 2 of 4 metrics space axioms, but... if it works, it works.

## TODO
My app isn't perfect, I want to change/improve:
- [ ] Cookie sessions instead of the header to identify users
- [ ] Mobile view. Sorry, it works and is pretty only on desktops
- [ ] Improve algorithm, because people generally enter the first elevator
- [ ] Refactor and encapsulate code
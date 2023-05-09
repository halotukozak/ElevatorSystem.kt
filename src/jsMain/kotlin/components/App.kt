import components.ElevatorList
import components.MyFooter
import components.MyLogo
import components.Welcome
import emotion.react.css
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import model.ElevatorStatus
import react.FC
import react.Props
import react.dom.html.ReactHTML.span
import react.useState
import ringui.*
import web.cssom.Display
import web.cssom.px

val App = FC<Props> {
    var elevatorsStatus: List<ElevatorStatus> by useState(emptyList())
    var waitingPassengers: Map<Int, Int> by useState(mapOf())
    var isInitialized by useState(false)
    var numberOfFloorsInput by useState(0)

    Header {
        theme = "light"

        Button {
            text = true
            href = "https://github.com/halotukozak/ElevatorSystem.kt"

            Heading {
                +"Elevator system"

                css { hover { color = web.cssom.Color("#1a98ff") } }

                span {
                    css {
                        display = Display.Companion.inline
                        marginLeft = 10.px
                    }

                    MyLogo {
                        name = "github"
                        height = 30.0
                    }
                }
            }
        }

        Tray {
            Link {
                onClick = {
                    scope.launch {
                        isInitialized = false
                        val status = getStatus()
                        elevatorsStatus = status.elevatorsStatus
                        waitingPassengers = status.waitingPassengers
                    }
                }
                +"reset system"
            }
        }
    }

    Dialog {
        show = !isInitialized
        Welcome {
            onSubmit = { numberOfElevators: Int, numberOfFloors: Int ->
                scope.launch {
                    init(numberOfElevators, numberOfFloors)
                    numberOfFloorsInput = numberOfFloors
                    isInitialized = true
                    async {
                        val status = getStatus()
                        elevatorsStatus = status.elevatorsStatus
                        waitingPassengers = status.waitingPassengers
                    }.await()
                }
            }
        }
    }

    if (elevatorsStatus.isNotEmpty()) {
        ElevatorList {
            elevators = elevatorsStatus
            passengers = waitingPassengers
            updateStatus = {
                scope.launch {
                    async {
                        val status = getStatus()
                        elevatorsStatus = status.elevatorsStatus
                        waitingPassengers = status.waitingPassengers
                    }.await()
                }
            }
            numberOfFloors = numberOfFloorsInput
        }

        MyFooter {}
    }
}


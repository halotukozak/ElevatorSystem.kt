import components.ElevatorList
import components.Welcome
import emotion.react.css
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import model.ElevatorStatus
import react.FC
import react.Props
import react.useState
import ringui.*
import web.cssom.FontFamily
import web.cssom.px

val App = FC<Props> {
    var elevatorsStatus: List<ElevatorStatus> by useState(emptyList())
    var isInitialized by useState(false)
    var numberOfFloorsInput by useState(0)

    Header {
        theme = "light"

        Heading {
            css {
                paddingTop = 10.px
                paddingBottom = 10.px
                fontFamily = FontFamily.monospace
            }
            +"Elevator system"
        }


        Tray {
            Link {
                onClick = {
                    scope.launch {
                        reset()
                        isInitialized = false
                        elevatorsStatus = emptyList()
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
                    elevatorsStatus = getStatus()
                }
            }
        }
    }

//    AlertService {}

    if (elevatorsStatus.isNotEmpty()) {
        ElevatorList {
            elevators = elevatorsStatus
            updateStatus = {
                scope.launch {
                    async { elevatorsStatus = getStatus() }.await()
                }
            }
            numberOfFloors = numberOfFloorsInput
        }
    }
}

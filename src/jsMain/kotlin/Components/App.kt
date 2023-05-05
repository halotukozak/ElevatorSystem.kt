import Components.ElevatorList
import Components.ErrorBadge
import Components.Welcome
import kotlinx.coroutines.launch
import model.Elevator
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.useEffectOnce
import react.useState

val App = FC<Props> {
    var elevatorsList: List<Elevator> by useState(emptyList())
    var isInitialized by useState(false)

    useEffectOnce { scope.launch { reset() } }

    ReactHTML.h1 { +"Elevator system" }

    ReactHTML.button {
        onClick = {
            scope.launch {
                reset()
                isInitialized = false
                elevatorsList = emptyList()
            }
        }
        +"Reset"
    }

    ErrorBadge {}

    Welcome {
        onSubmit = { numberOfElevators: Int, numberOfFloors: Int ->
            scope.launch {
                init(numberOfElevators, numberOfFloors)
                isInitialized = true
                elevatorsList = getElevators()
            }
        }
        isVisible = !isInitialized
    }



    if (elevatorsList.isNotEmpty()) {
        ElevatorList {
            elevators = elevatorsList
        }
    }
}

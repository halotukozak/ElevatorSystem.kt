import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.Elevator
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import web.dom.document

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find container!")
    createRoot(container).render(App.create())
}

val scope = MainScope()

val App = FC<Props> {
    var elevatorsList: List<Elevator> by useState(emptyList())
    var isInitialized by useState(false)

    useEffectOnce { scope.launch { reset() } }

    h1 { +"Elevator system" }

    button {
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





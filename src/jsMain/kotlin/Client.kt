import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import web.dom.document

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find container!")
    createRoot(container).render(App.create())
}

private val scope = MainScope()

val App = FC<Props> {
    var elevators by useState(emptyList<Elevator>())

    useEffectOnce {
        scope.launch {
            elevators = getElevators()
        }
    }

    h1 {
        +"Elevator system"
    }
    ul {
        for (item in elevators.sortedByDescending(Elevator::id)) {
            li {
                key = item.toString()
                +"[${item.id}]"
            }
        }
    }
}


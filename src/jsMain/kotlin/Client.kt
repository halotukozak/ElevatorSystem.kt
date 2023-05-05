import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import web.dom.document

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find container!")
    createRoot(container).render(App.create())
    container.append(Welcome)
}

val scope = MainScope()

val App = FC<Props> {
    var elevators by useState(emptyList<Elevator>())

    h1 {
        +"Elevator system"
    }
    useEffectOnce {
        scope.launch {
            elevators = getElevators()
        }
    }

    Welcome {

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

external interface ErrorBadgeProps : Props {
    var content: String?
}

val ErrorBadge = FC<ErrorBadgeProps> {

}


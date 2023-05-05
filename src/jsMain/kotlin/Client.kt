import Components.ElevatorList
import Components.ErrorBadge
import Components.Welcome
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






import react.FC
import react.Props
import react.createContext
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.useContext


val errorContext = createContext<ArrayDeque<String>>(ArrayDeque())


val ErrorBadge = FC<Props> { props ->

    val errors = useContext(errorContext)

    div {
        for (error in errors) {
            span {
                +error
            }
        }
    }
}
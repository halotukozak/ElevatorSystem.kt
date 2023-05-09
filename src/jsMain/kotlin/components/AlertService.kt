package components

import react.FC
import react.Props
import react.createContext
import react.useContext
import ringui.Alert

val errorContext = createContext<ArrayDeque<String>>(ArrayDeque())

val AlertService = FC<Props> { props ->
    val errors = useContext(errorContext)
    Alert {
        errors.firstOrNull()
    }
}

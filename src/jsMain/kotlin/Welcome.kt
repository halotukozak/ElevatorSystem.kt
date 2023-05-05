import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h4
import react.dom.html.ReactHTML.input
import web.html.ButtonType
import web.html.HTMLFormElement
import web.html.InputType

external interface WelcomeProps : Props {
    var numberOfElevators: Int
    var numberOfFloors: Int


}

val Welcome = FC<WelcomeProps> { props ->
    var numberOfElevators = 5
    var numberOfFloors = 5

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        scope.launch { init(props.numberOfElevators, props.numberOfFloors) }
    }
    div {
        form {
            onSubmit = submitHandler

            h2 {
                +"Hello, pick number of elevators and floors"
            }

            h4 {
                +"number of elevators"
            }
            input {
                type = InputType.number
                value = numberOfElevators
                min = 0
                max = Config.maxNumberOfElevators
                onChange = { event ->
                    numberOfElevators = event.target.value.toInt()
                }
            }

            br

            h4 {
                +"number of floors"
            }
            input {
                type = InputType.number
                value = numberOfFloors
                min = 0
                max = Config.maxNumberOfFloors
                onChange = { event ->
                    numberOfFloors = event.target.value.toInt()
                }
            }
            button {
                type = ButtonType.submit
                +"Submit"
            }
        }
    }
}


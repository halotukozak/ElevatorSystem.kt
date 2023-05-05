import react.FC
import react.Props
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.useState
import web.html.ButtonType
import web.html.HTMLFormElement
import web.html.InputType

external interface WelcomeProps : Props {
    var numberOfElevators: Int
    var numberOfFloors: Int


}

val Welcome = FC<WelcomeProps> { props ->
    var numberOfElevators by useState(props.numberOfElevators)
    var numberOfFloors by useState(props.numberOfFloors)
    div {
        +"Hello, pick number of elevators and floors"
    }
    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        listOf(props.numberOfElevators, props.numberOfFloors)

    }

    form {
        onSubmit = submitHandler
        input {
            type = InputType.number
            value = numberOfElevators
            min = 0
            max = Config.maxNumberOfElevators
            onChange = { event ->
                numberOfElevators = event.target.value.toInt()
            }
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
        }
    }
}


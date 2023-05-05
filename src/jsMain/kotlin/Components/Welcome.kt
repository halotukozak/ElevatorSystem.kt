package Components

import Config
import emotion.react.css
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
import react.useState
import web.cssom.Visibility
import web.html.ButtonType
import web.html.HTMLFormElement
import web.html.InputType


external interface WelcomeProps : Props {
    var isVisible: Boolean
    var onSubmit: (Int, Int) -> Unit

}

val Welcome = FC<WelcomeProps> { props ->

    val (numberOfElevators, setNumberOfElevators) = useState(0)
    val (numberOfFloors, setNumberOfFloors) = useState(0)

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        props.onSubmit(numberOfElevators, numberOfFloors)
    }


    div {
        css {
            visibility = if (props.isVisible) null else Visibility.hidden
        }
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
                min = 1
                max = Config.maxNumberOfElevators
                onChange = { setNumberOfElevators(it.target.value.toInt()) }
            }

            br

            h4 {
                +"number of floors"
            }
            input {
                type = InputType.number
                value = numberOfFloors
                min = 1
                max = Config.maxNumberOfFloors
                onChange = { setNumberOfFloors(it.target.value.toInt()) }
            }
            button {
                type = ButtonType.submit
                +"Submit"
            }
        }
    }
}


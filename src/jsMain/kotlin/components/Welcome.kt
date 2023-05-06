package components

import Config
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.useState
import ringui.*
import web.cssom.Display
import web.cssom.Margin
import web.cssom.px
import web.html.InputType


external interface WelcomeProps : Props {
    var onSubmit: (Int, Int) -> Unit

}

val Welcome = FC<WelcomeProps> { props ->

    val (numberOfElevators, setNumberOfElevators) = useState(3)
    val (numberOfFloors, setNumberOfFloors) = useState(10)

    val submitHandler = {
        props.onSubmit(numberOfElevators, numberOfFloors)
    }

    form {
        Island {
            IslandHeader {
                Heading { +"Hello, pick number of elevators and floors" }
            }
            IslandContent {
                Grid {
                    Row {
                        Col {
                            Heading {
                                css { display = Display.inlineBlock }
                                level = 3
                                +"Enter number of elevators"
                            }
                        }

                        Col {
                            css {
                                display = Display.flex
                            }
                            input {
                                type = InputType.number
                                value = numberOfElevators
                                min = 1
                                max = Config.maxNumberOfElevators
                                onChange = { setNumberOfElevators(it.target.value.toInt()) }
                            }
                        }
                    }


                    Row {
                        Col {
                            Heading {
                                css { display = Display.inlineBlock }
                                level = 3
                                +"Enter number of floors"
                            }
                        }
                        Col {
                            input {
                                type = InputType.number
                                value = numberOfFloors
                                min = 1
                                max = Config.maxNumberOfFloors
                                onChange = { setNumberOfFloors(it.target.value.toInt()) }
                            }
                        }
                    }
                    Row {
                        Button {
                            css {
                                margin = Margin(vertical = 10.px, horizontal = 0.px)
                                display = Display.block
                            }
                            onMouseDown = { submitHandler() }
                            +"Submit"
                        }
                    }
                }

            }
        }
    }
}


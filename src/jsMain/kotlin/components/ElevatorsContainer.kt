package components

import emotion.react.css
import model.ElevatorStatus
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.span
import web.cssom.ClassName
import web.cssom.Color

external interface ElevatorListProps : Props {
    var elevators: List<ElevatorStatus>
}

val ElevatorList = FC<ElevatorListProps> { props ->
    div {
        className = ClassName("shaft")
        props.elevators.forEachIndexed { index, item ->
            div {
                h3 { +"Elevator $index" }
                h2 {
                    +"Current floor ${item.currentLevel}"
                }
                h2 {
                    repeat(item.passengers.size) {
                        span {
                            css {
                                color = Color("red")
                            }
                            +"*"
                        }
                    }
                }


            }
        }
    }
}
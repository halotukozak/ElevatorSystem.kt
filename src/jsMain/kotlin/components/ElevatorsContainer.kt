package components

import emotion.react.css
import model.ElevatorStatus
import react.FC
import react.Props
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.i
import ringui.*
import web.cssom.*

external interface ElevatorListProps : Props {
    var elevators: List<ElevatorStatus>
    var numberOfFloors: Int
}

val ElevatorList = FC<ElevatorListProps> { props ->
    Grid {
        Row {
            around = RowPosition.xs
            css {
                borderBottom = Border(1.px, LineStyle.solid)
                height = 80.vh / props.numberOfFloors
            }
            props.elevators.forEach { item ->
                Col {
                    h2 {
                        css {
                            fontSize = FontSize.xxLarge
                        }
                        +"${item.currentLevel}"
                    }
                }
            }
            Col {
                Button {
//                    i {
//                        className = ClassName("fa-regular fa-forward-step")
//                    }
                    +"➤"
                }
            }
        }

        for (i in 0..props.numberOfFloors) {
            Row {
                around = RowPosition.xs
                css {
                    borderBottom = Border(1.px, LineStyle.solid)
                    height = 70.vh / props.numberOfFloors
                }
                props.elevators.forEachIndexed { index, item ->
                    Col {
                        if (i == item.currentLevel)
//                            i {
//                                className = ClassName("fa-solid fa-rectangle-vertical")
//                            }
                        Heading{
                            +"⌷"
                        }
                    }
                }
                Col {
                    ButtonGroup {
                        Button {
//                            i {
//                                className = ClassName("fa-regular fa-arrow-up")
//                            }
                            +"↑"
                        }
                        Button {
//                            i {
//                                className = ClassName("fa-regular fa-arrow-down")
//                            }
                            +"↓"
                        }
                    }
                }
            }
        }
    }
}

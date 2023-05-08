package components

import Direction.DOWN
import Direction.UP
import emotion.react.css
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import model.ElevatorStatus
import pickup
import react.FC
import react.Props
import react.dom.html.ReactHTML.h2
import react.useState
import ringui.*
import scope
import step
import web.cssom.*

external interface ElevatorListProps : Props {
    var elevators: List<ElevatorStatus>
    var numberOfFloors: Int
    var updateStatus: () -> Unit
}

val ElevatorList = FC<ElevatorListProps> { props ->
    val floors = (0..props.numberOfFloors)
    var pickingFloor by useState(false)
    var currFloor by useState(-1)
    var currDirection by useState(UP)

    Grid {
        Row {
            around = RowPosition.xs
            css {
                borderBottom = Border(1.px, LineStyle.solid)
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
                xs = 1
                Button {
                    css{
                        fontSize = FontSize.large
                        height = 50.px
                    }
                    +"step ➤"
                    primary = true
                    onMouseDown = {
                        scope.launch {
                            async { step() }.await()
                            props.updateStatus()
                        }
                    }
                }
            }
        }

        floors.reversed().forEach { i ->
            Row {
                around = RowPosition.xs
                css {
                    borderBottom = Border(1.px, LineStyle.solid)
                }
                props.elevators.forEach { item ->
                    Col {
                        if (i == item.currentLevel)
                            Heading {
                                css {
                                    width = 0.px
                                }
                                +("[" + ("*".repeat(item.passengers.count { !it.isWaiting })) + "]")
                            }
                    }
                }
                Col {
                    xs = 1
                    Grid {
                        Row {
                            Col {
                                css {
                                    marginBottom = 0.px
                                }
                                Button {
                                    +"↑"
                                    onMouseDown = {
                                        currFloor = i
                                        pickingFloor = true
                                        currDirection = UP
                                    }
                                }
                            }
                            Col {
                                css {
                                    marginBottom = 0.px
                                    marginLeft = 3.px
                                }
                                Heading {
                                    level = 4
                                    +("*".repeat(props.elevators.sumOf { e -> e.passengers.count { it.isWaiting && it.startingFloor == i } }))
                                }
                            }
                        }

                        Row {
                            Col {
                                css {
                                    marginTop = 0.px
                                }
                                Button {
                                    +"↓"
                                    onMouseDown = {
                                        currFloor = i
                                        pickingFloor = true
                                        currDirection = DOWN
                                    }
                                }
                            }
                            Col {
                                css {
                                    marginTop = 0.px
                                    marginLeft = 3.px
                                }
                                Heading {
                                    level = 3
                                    +"floor $i "
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Dialog {
        show = pickingFloor
        className = ClassName("floorDialog")
        FloorPicker {
            floorsRange = floors
            onSubmit = { floor ->
                scope.launch {
                    pickup(currFloor, floor, currDirection)
                    pickingFloor = false
                    props.updateStatus()
                }
            }
        }
    }
}




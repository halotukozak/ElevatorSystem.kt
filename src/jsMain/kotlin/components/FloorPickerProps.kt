package components

import emotion.react.css
import react.FC
import react.Props
import ringui.*
import web.cssom.ClassName
import web.cssom.FontSize

external interface FloorPickerProps : Props {
    var floorsRange: IntRange
    var onSubmit: (Int) -> Unit
}

val FloorPicker = FC<FloorPickerProps> { props ->
    AdaptiveIsland {
        IslandHeader {
            border = true
            +"Pick floor"
        }
        IslandContent {
            className = ClassName("floorPicker")
            Grid {
                props.floorsRange.reversed().chunked(3).forEach { nrs ->
                    Row {
                        around = RowPosition.xs
                        nrs.forEach { nr ->
                            Col {
                                Button {
                                    short = true
                                    css { fontSize = FontSize.medium }
                                    +nr.toString()

                                    onMouseDown = { props.onSubmit(nr) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

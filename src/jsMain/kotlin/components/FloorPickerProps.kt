package components

import emotion.react.css
import react.FC
import react.Props
import ringui.*
import web.cssom.px

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
            Grid {
                props.floorsRange.reversed().chunked(3).forEach { nrs ->
                    Row {
                        center = RowPosition.xs
                        nrs.forEach { nr ->
                            Col {
                                Button {
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

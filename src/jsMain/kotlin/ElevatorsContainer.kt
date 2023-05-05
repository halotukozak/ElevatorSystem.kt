import model.Elevator
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

external interface ElevatorListProps : Props {
    var elevators: List<Elevator>
}

val ElevatorList = FC<ElevatorListProps> { props ->
    ul {
        props.elevators.forEachIndexed { index, item ->
            li {
                +"Elevator nr $index ID[${item.id}]"
            }
        }
    }
}
package components

import react.FC
import react.Props
import react.dom.html.ReactHTML

external interface LogoProps : Props {
    var name: String
    var height: Double?
}

val MyLogo = FC<LogoProps> { props ->
    ReactHTML.img {
        src = "logos/${props.name}.svg"
        alt = "${props.name} logo"
        height = props.height ?: 50.0
    }
}
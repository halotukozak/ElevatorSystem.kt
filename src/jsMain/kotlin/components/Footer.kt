package components

import emotion.react.css
import react.FC
import react.Props
import ringui.*
import web.cssom.px

val MyFooter = FC<Props> {
    Grid {
        Row {
            middle = RowPosition.xs
            start = RowPosition.xs
            Col {
                Heading {
                    level = 3
                    +"made by"
                }
            }
            Col {
                Button {
                    text = true
                    href = "https://hyperskill.org/profile/243767975"
                    +"Bartłomiej Kozak"
                }
            }
            Col {
                Heading {
                    level = 3
                    +"with"
                }
            }
            for (s in listOf("kotlin", "ktor", "ring-ui")) {
                Col {
                    css { paddingLeft = 10.px }
                    MyLogo { name = s }
                }
            }
        }
    }
}


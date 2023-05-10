package components

import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import ringui.*
import web.cssom.FontSize
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
                    css { fontSize = FontSize.larger }

                    +"Bartłomiej Kozak"
                }
            }
            Col {
                Heading {
                    level = 3
                    +"with"
                }
            }
            for ((technologyName, link) in mapOf(
                "kotlin" to "https://kotlinlang.org",
                "ktor" to "https://ktor.io",
                "ring-ui" to "https://jetbrains.github.io/ring-ui/master/?path=/docs/ring-ui-welcome--docs",
                "react" to "https://react.dev"
            )) {
                Col {
                    css { paddingLeft = 10.px }
                    a {
                        href = link
                        MyLogo { name = technologyName }
                    }
                }
            }
        }
    }
}


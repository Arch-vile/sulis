package moonillusions.sulis.domain

import org.joda.time.LocalDate


class Game {

    Player servingPlayer
    Player receivingPlayer
    LocalDate date
    Integer servingPlayerPoints
    Integer receivingPlayerPoints

    static mapping = {
        servingPlayer cascade: 'save-update'
        receivingPlayer cascade: 'save-update'
    }

    static constraints = {
        servingPlayer nullable: false
        receivingPlayer nullable: false
        date nullable: false
        servingPlayerPoints nullable: false, min: 0
        receivingPlayerPoints nullable: false, min: 0
    }
}

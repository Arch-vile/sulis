package moonillusions.sulis.commands

import grails.validation.Validateable

import org.joda.time.LocalDate

@Validateable
class CreateGameCommand {

    String newServingPlayer
    String newReceivingPlayer
    Date date
    Integer servingPlayerPoints
    Integer receivingPlayerPoints
    Long servingPlayerId
    Long receivingPlayerId

    def getDate() {
        return new LocalDate(date)
    }
}

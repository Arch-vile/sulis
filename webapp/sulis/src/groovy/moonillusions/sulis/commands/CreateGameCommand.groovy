package moonillusions.sulis.commands

import grails.validation.Validateable

@Validateable
class CreateGameCommand {

    String newServingPlayer
    String newReceivingPlayer
    Date date
    Integer servingPlayerPoints
    Integer receivingPlayerPoints
    Long servingPlayerId
    Long receivingPlayerId

    static constraints = {
        newServingPlayer size: 3..20, nullable: true
        newReceivingPlayer size: 3..20, nullable: true
        servingPlayerPoints range: 0..25
        receivingPlayerPoints range: 0..25
        servingPlayerId nullable: true
        receivingPlayerId nullable: true
    }
}

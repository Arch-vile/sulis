package moonillusions.sulis.commands

import grails.validation.Validateable

@Validateable
class CreateGameCommand {

    Date date = new Date()
    Integer servingPlayerPoints
    Integer receivingPlayerPoints
    Long servingPlayerId
    Long receivingPlayerId

    static constraints = {
        servingPlayerPoints range: 0..25
        receivingPlayerPoints range: 0..25
        receivingPlayerId validator: { val, obj ->
            if(val == obj.servingPlayerId) return "bothSame"
        }
    }
}

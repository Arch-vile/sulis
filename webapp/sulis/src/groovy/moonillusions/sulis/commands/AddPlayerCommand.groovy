package moonillusions.sulis.commands

import grails.validation.Validateable
import moonillusions.sulis.service.PlayerService

@Validateable
class AddPlayerCommand {

    PlayerService playerService

    String name

    static constraints = {
        name size: 3..20, validator: { val, obj ->
            if(obj.playerService.get(val)) return "alreadyExists"
        }
        playerService nullable: true
    }
}

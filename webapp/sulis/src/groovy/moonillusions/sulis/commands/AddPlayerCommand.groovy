package moonillusions.sulis.commands

import grails.validation.Validateable
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.PlayerService

@Validateable
class AddPlayerCommand {

    PlayerService playerService
    Player player

    String name


    static constraints = {
        name size: 3..20, validator: { val, obj ->
            if(obj.playerService.get(val)) return "alreadyExists"
        }
        playerService nullable: true
    }

    def getPlayer() {
        if(!player) {
            player = new Player(name: name)
        }
        player
    }
}

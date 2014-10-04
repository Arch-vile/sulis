package moonillusions.sulis.controllers

import moonillusions.sulis.commands.AddPlayerCommand
import moonillusions.sulis.service.PlayerService


class PlayerController {

    PlayerService playerService

    def index() {
        render view: 'add'
    }

    def add(AddPlayerCommand command) {
        if(playerService.create(command.getPlayer())) {
            render(view: "/game/")
        } else {
            command
        }
    }
}

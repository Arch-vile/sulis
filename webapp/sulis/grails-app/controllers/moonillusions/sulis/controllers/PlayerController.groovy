package moonillusions.sulis.controllers

import moonillusions.sulis.commands.AddPlayerCommand
import moonillusions.sulis.service.PlayerService


class PlayerController {

	public static final String MODEL_COMMAND = "command"
	
    PlayerService playerService

    def index() {
        render view: 'add'
    }

    def add(AddPlayerCommand command) {
        if(playerService.create(command.getPlayer())) {
            flash.message = "player.message.created.ok"
            redirect(controller: "game")
        } else {
            [(MODEL_COMMAND): command]
        }
    }
}

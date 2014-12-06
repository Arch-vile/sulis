package moonillusions.sulis.controllers

import moonillusions.sulis.commands.CreateGameCommand
import moonillusions.sulis.domain.Game
import moonillusions.sulis.service.GameService
import moonillusions.sulis.service.PlayerService

import org.codehaus.groovy.grails.exceptions.GrailsRuntimeException

class GameController {

	public static final String MODEL_COMMAND = "command"
	public static final String MODEL_PLAYERS = "players"
	
    PlayerService playerService
    GameService gameService

    def afterInterceptor = [action: this.&setPlayerList]

    private setPlayerList(model) {
        model[MODEL_PLAYERS] = playerService.list()
    }

    def index() {
        render view: 'create', model: [(MODEL_COMMAND): new CreateGameCommand()]
    }

    def create(CreateGameCommand command) {

        if(command.hasErrors()) {
            return [(MODEL_COMMAND): command]
        }

        Game newGame = gameService.create(command.game)
        if(newGame) {
            flash.message = message(code: 'gameController.message.game.created')
            render view: 'show', model: [game: newGame]
        } else {
            throw new GrailsRuntimeException("Could not create the new game")
        }
    }
}
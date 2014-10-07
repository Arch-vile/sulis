package moonillusions.sulis.controllers

import moonillusions.sulis.commands.CreateGameCommand
import moonillusions.sulis.domain.Game
import moonillusions.sulis.service.GameService
import moonillusions.sulis.service.PlayerService

import org.codehaus.groovy.grails.exceptions.GrailsRuntimeException

class GameController {

    PlayerService playerService
    GameService gameService

    def afterInterceptor = [action: this.&setPlayerList]

    private setPlayerList(model) {
        model.players = playerService.list()
    }

    def index() {
        render view: 'create', model: [command: new CreateGameCommand()]
    }

    def create(CreateGameCommand command) {

        println "create" + command.hasErrors()
        if(command.hasErrors()) {
            return [command: command]
        }

        println command.game
        Game newGame = gameService.create(command.game)
        println newGame
        if(newGame) {
            flash.message = message(code: 'gameController.message.game.created')
            render view: 'show', model: [game: newGame]
        } else {
            throw new GrailsRuntimeException("Could not create the new game")
        }
    }
}
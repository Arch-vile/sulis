package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.GameService
import moonillusions.sulis.service.PlayerService

import org.joda.time.LocalDate

class GameController {

    PlayerService playerService
    GameService gameService

    def afterInterceptor = { model ->
        model.players = playerService.list()
    }

    def index(CreateGameCommand command) {
        command.game = new Game(date: new LocalDate())
        render view: 'create', model: [createGameCommand: command]
    }

    def create(CreateGameCommand command) {

        if(command.newServingPlayer) {
            command.game.servingPlayer = new Player(name: command.newServingPlayer)
        }

        if(command.newReceivingPlayer) {
            command.game.receivingPlayer = new Player(name: command.newReceivingPlayer)
        }

        Game created = gameService.create(command.game)
        if( created ) {
            flash.message = message(code: 'gameController.message.game.created')
            render view: 'show', model: [game: created]
        }

        return [createGameCommand: command];
    }
}

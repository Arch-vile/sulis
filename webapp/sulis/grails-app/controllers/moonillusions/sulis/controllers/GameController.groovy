package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;
import org.joda.time.LocalDate

class GameController {
	
	PlayerService playerService
	GameService gameService
	
	def afterInterceptor = { model ->
        model.players = playerService.list()
    }
	
	def index(CreateGameCommand command) {
		command.game = new Game(date: new LocalDate())
		render view: 'create', model: [command: command]
	}
	
	def create(CreateGameCommand command) {
		
		if(command.newServingPlayerName) {
			command.game.player1 = new Player(name: command.newServingPlayerName)
		}
		
		if(command.newReceivingPlayerName) {
			command.game.player2 = new Player(name: command.newReceivingPlayerName)
		}
		
		Game created = gameService.create(command.game)
		if( created ) {
			flash.message = message(code: 'gameController.message.game.created')
			render view: 'show', model: [game: created]
		}
		
		return [command: command];
		
   }
	
	
}

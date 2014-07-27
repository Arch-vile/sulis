package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;

class GameController {
	
	PlayerService playerService
	GameService gameService
	
    def index() { 
		[ players: playerService.list() ]
	}
	
	def create() {
		Game game = new Game(params)
		
		if(params.newServingPlayer) {
			game.player1 = new Player(name: params.newServingPlayer)
		}
		
		if(params.newReceivingPlayer) {
			game.player2 = new Player(name: params.newReceivingPlayer)
		}
		
		Game created = gameService.create(game)
		if( created ) {
			flash.message = message(code: 'gameController.message.game.created')
			render view: 'show', model: [game: created]
		} else {
			chain action: 'index', model: [game: game]
		}
   }
	
	
}

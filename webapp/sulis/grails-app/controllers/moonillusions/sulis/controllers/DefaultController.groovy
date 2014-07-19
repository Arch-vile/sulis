package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;

class DefaultController {
	
	PlayerService playerService
	GameService gameService
	
    def index() { 
		render(view: 'home', model: [players: playerService.list()])
	}
	
	def create() {
		Game game = new Game(params)
		
		if(params.newServingPlayer) {
			game.player1 = new Player(name: params.newServingPlayer)
		}
		
		
		gameService.create(game)
	}
}

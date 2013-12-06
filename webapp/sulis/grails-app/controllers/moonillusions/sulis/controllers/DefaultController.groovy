package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.PlayerService;

class DefaultController {
	
	PlayerService playerService

    def index() { 
		render(view: 'home', model: [players: playerService.list()])
	}
}

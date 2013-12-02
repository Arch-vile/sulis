package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Player

class DefaultController {

    def index() { 
		render(view: 'home', model: [players: []])
	}
}

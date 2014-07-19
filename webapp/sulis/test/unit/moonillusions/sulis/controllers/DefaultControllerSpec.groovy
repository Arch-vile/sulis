

package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player;
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo

import org.spockframework.compiler.model.ThenBlock;

import grails.test.mixin.TestFor
import spock.lang.Specification
import static moonillusions.grails.testing.matchers.ControllerView.renders
import static moonillusions.grails.testing.matchers.ControllerModel.hasModel
import static spock.util.matcher.HamcrestSupport.that

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(DefaultController)
class DefaultControllerSpec extends Specification {

	PlayerService playerService = Mock(PlayerService)
	GameService gameService = Mock(GameService)
	
	def setup() {
		controller.playerService = playerService
		controller.gameService = gameService
	}

	def cleanup() {
	}

	void "Index action should redirect to home page"() {
		when:
		controller.index()
		
		then:
		that controller, renders("/default/home")
	}
	
	void "Index action should have all players in model"() {
		setup:
		Player player1 = new Player()
		Player player2 = new Player()
		playerService.list() >> [player1,player2]
		
		when:
		controller.index()
		
		then:
		that controller, hasModel('players',containsInAnyOrder(player2, player1))
	}
	
	void "If inserted, the new serving player is used"() {
		
		when:
		params.newServingPlayer = 'newSPlayer'
		controller.create()
		
		then:
		1 * gameService.create({ Game game -> 
			assert game.player1.name == "newSPlayer"
			true 
		})
	}
	
	
	
}
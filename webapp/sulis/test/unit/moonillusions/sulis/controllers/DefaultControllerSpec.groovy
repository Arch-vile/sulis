
package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Player;
import moonillusions.sulis.service.PlayerService;
import static org.hamcrest.Matchers.containsInAnyOrder

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
	
	def setup() {
		controller.playerService = playerService
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
	
}
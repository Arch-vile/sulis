package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player;
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo

import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse;
import org.joda.time.LocalDate;
import org.spockframework.compiler.model.ThenBlock;

import grails.test.mixin.TestFor
import spock.lang.Specification
import static spock.util.matcher.HamcrestSupport.that

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GameController)
class GameControllerSpec extends Specification {

	PlayerService playerService = Mock(PlayerService)
	GameService gameService = Mock(GameService)
	
	def setup() {
		controller.playerService = playerService
		controller.gameService = gameService
	}

	def cleanup() {
	}

	void "Index action should redirect to index page"() {
		when:
		controller.index()
		
		then:
		view == null
	}
	
	void "Index action should have all players in model"() {
		setup:
		Player player1 = new Player()
		Player player2 = new Player()
		playerService.list() >> [player1,player2]
		
		when:
		def model = controller.index()
		
		then:
		that model.players, containsInAnyOrder(player2, player1)
	}
	
	void "Players and scores used for new game"() {
	
		when:
		params.'player1.name' = "serving player"
		params.'player2.name' = "receiving player"
		params.points1 = "21"
		params.points2 = "10"
		params.date = "2014-07-21"
		controller.create()
		
		then:
		1 * gameService.create({ Game game -> 
			assert game.player1.name == "serving player"
			assert game.player2.name == "receiving player"
			assert game.points1 == 21
			assert game.points2 == 10
			assert game.date == new LocalDate("2014-07-21")
			true
		})
	}
	
	void "If inserted, the new serving player is used for new game"() {
		
		when:
		params.newServingPlayer = 'new player'
		params.'player1.name' = "serving player"
		controller.create()
		
		then:
		1 * gameService.create({ Game game -> 
			assert game.player1.name == "new player"
			true 
		})
	}
	
	void "If inserted, the new receiving player is used for new game"() {
		
		when:
		params.newReceivingPlayer = 'new player'
		params.'player2.name' = "receiving player"
		controller.create()
		
		then:
		1 * gameService.create({ Game game ->
			assert game.player2.name == "new player"
			true
		})
	}
	
	
	void "If errors, the default view is shown and failed game attached" () {
		
		when:
		gameService.create(_) >> null
		controller.create()
		
		then: 'the failed game is passed forward'
		controller.chainModel.game != null
		
		and: 'control passed to the index'
		response.redirectUrl == '/' // Action is mapped to '/' because this is our default controller

	}
	
	
}
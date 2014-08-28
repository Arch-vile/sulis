package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player;
import moonillusions.sulis.service.GameService;
import moonillusions.sulis.service.PlayerService;
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo

import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse;
import org.codehaus.groovy.runtime.metaclass.NewStaticMetaMethod;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate
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
	LocalDate currentDate
	
	def setup() {
		controller.playerService = playerService
		controller.gameService = gameService
		currentDate = new LocalDate().minusDays(10);
		DateTimeUtils.setCurrentMillisFixed(currentDate.getLocalMillis())
		
	}

	def cleanup() {
	}
	
	
	void "After interceptor is applied to all actions and populates model with players list"() {
		
		setup: 'Mock playerService player listing'
		def listOfPlayers = [new Player()]
		playerService.list() >> listOfPlayers
		
		when: 'Interceptor is applied to actions models'
		def model = [name: "some"]
		controller.afterInterceptor(model)
		
		then: 'Player list is populated'
		model.players == listOfPlayers
		model.name == "some"
		
	}
	
	void "Index action renders the create view with prepopulated command"() {
		
		when:
		controller.index()
		
		then: 'Create view is shown'
		view == '/game/create'
		
		and: 'Command has the current data populated'
		model.command.game.date == currentDate
		
	}

	
	void "Create action creates new game"() {
	
		setup: 
		def game = new Game()
		def commandObject = new CreateGameCommand(game: game)

		when: 'create action is called'
		controller.create(commandObject)
		
		then: 'Service is called with the game'
		1 * gameService.create(game)
	}
	
	void "If given, the new serving player is used for new game"() {
		
		given: 'Command with new player'
		def command = new CreateGameCommand(newServingPlayer: "new player", game: new Game())
		
		when: 'Create action is called'
		controller.create(command)
		
		then:
		1 * gameService.create({ Game game -> 
			assert game.servingPlayer.name == "new player"
			true 
		})
	}
	
	void "If given, the new receiving player is used for new game"() {
		
		given: 'Command with new player'
		def command = new CreateGameCommand(newReceivingPlayer: "new player", game: new Game())
		
		when: 'Create action is called'
		controller.create(command)
		
		then:
		1 * gameService.create({ Game game -> 
			assert game.receivingPlayer.name == "new player"
			true 
		})
	}
	
	void "If game created, the show view is shown"() {

		setup: 'Service creates new game'
		Game game = new Game()
		gameService.create(_) >> game
				
		when:
		controller.create(new CreateGameCommand())
		
		then: 'Correct view is shown'
		view == '/game/show'
		
		and: "Created game is given as model"
		model.game == game
		
		and: "Flash message indicating success is shown"
		flash.message == 'gameController.message.game.created'
	}
	
	
	void "If errors on service, the same view is shown with same command" () {
		
		setup: 'Service call fails to create game'
		gameService.create(_) >> null
		
		when: 'Create new game'
		def command = new CreateGameCommand()
		def model = controller.create(command)
		
		then: 'the failed game is passed forward'
		model.command == command
		
		and: 'create view is shown again per conventions'
		view == null

	}
	
	
	
}
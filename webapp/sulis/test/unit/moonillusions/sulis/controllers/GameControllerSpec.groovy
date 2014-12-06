package moonillusions.sulis.controllers

import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo
import static spock.util.matcher.HamcrestSupport.that
import grails.test.mixin.*
import moonillusions.sulis.commands.CreateGameCommand
import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player
import moonillusions.sulis.service.GameService
import moonillusions.sulis.service.PlayerService

import org.codehaus.groovy.grails.exceptions.GrailsRuntimeException
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse
import org.joda.time.DateTimeUtils
import org.joda.time.LocalDate

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GameController)
class GameControllerSpec extends Specification {

    PlayerService playerService = Mock(PlayerService)
    GameService gameService = Mock(GameService)

    //LocalDate currentDate
    def listOfPlayers = [new Player()]
    CreateGameCommand mockedValidCommand
    Game gameFromComand = new Game()

    def setup() {
        controller.playerService = playerService
        controller.gameService = gameService
        //currentDate = new LocalDate().minusDays(30);
        //DateTimeUtils.setCurrentMillisFixed(currentDate.getLocalMillis())
        playerService.list() >> listOfPlayers

        mockedValidCommand = Mock(CreateGameCommand)
        mockedValidCommand.hasErrors() >> false
        mockedValidCommand.getGame() >> gameFromComand
    }

    def cleanup() {
    }

    void "afterInterceptor is applied to all actions"() {
        expect: 'Applied to all controller methods'
        controller.afterInterceptor.only == null
        controller.afterInterceptor.except == null
    }


    void "afterInterceptor populates player list to the model"() {

        when: 'Interceptor is applied'
        def model = [name: "some"]
        controller.afterInterceptor.action.doCall(model)

        then: 'Player list is populated'
        model[GameController.MODEL_PLAYERS] == listOfPlayers
        model.name == "some"
    }

    void "Index action renders the create view and attach command to model"() {

        when:
        controller.index()

        then: 'Create view is shown'
        view == '/game/create'

        and: 'CreateGameCommand attached to model'
        model[GameController.MODEL_COMMAND] instanceof CreateGameCommand
    }


    void "Game from the command is created by service"() {

        when:
        controller.create(mockedValidCommand)

        then:
        1 * gameService.create(gameFromComand) >> gameFromComand
    }

    void "On invalid command, create action redisplays create view"() {

        setup: 'Mock the invalid command'
        def command = Mock(CreateGameCommand)
        command.hasErrors() >> true

        when: 'Called with invalid command'
        def model = controller.create(command)

        then: 'Create view redisplayed'
        model[GameController.MODEL_COMMAND] == command
    }

    void "If game created, the show view is shown"() {

        setup: 'Service successfully creates new game'
        Game game = new Game()
        gameService.create(_) >> game

        when:
        controller.create(mockedValidCommand)

        then: 'Correct view is shown'
        view == '/game/show'

        and: "Created game is given as model"
        model.game == game

        and: "Flash message indicating success is shown"
        flash.message == 'gameController.message.game.created'
    }


    void "If errors on service, throw error" () {

        setup: 'Service call fails to create game'
        gameService.create(_) >> null

        when: 'Create new game'
        def model = controller.create(mockedValidCommand)

        then:
        thrown(GrailsRuntimeException)
    }
}
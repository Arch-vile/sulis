package moonillusions.sulis.domain.integration



import static com.moonillusions.propertynavigation.PropertyNavigation.of
import static com.moonillusions.propertynavigation.PropertyNavigation.prop
import moonillusions.sulis.controllers.GameController
import moonillusions.sulis.domain.Game
import moonillusions.sulis.service.GameService

import org.joda.time.LocalDate

import spock.lang.*

/**
 *
 */
class GameControllerSpec extends Specification {

    GameService gameService
    GameController controller

    def setup() {
        controller = new GameController()
        gameService =  Mock(GameService)
        controller.gameService = gameService
    }

    def cleanup() {
    }


    void "request parameters mapped to command object"() {

        setup:
        controller.params['newServingPlayer'] = "Someone"
        controller.params['game.date'] = "21.1.2014"

        when:
        controller.create()

        then:
        1 * gameService.create({ Game game ->
            assert game.servingPlayer.name == "Someone"
            assert game.date == new LocalDate(2014,1,21)
            true
        })
    }
}

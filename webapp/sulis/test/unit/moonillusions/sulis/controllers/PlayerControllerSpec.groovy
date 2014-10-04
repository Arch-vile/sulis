package moonillusions.sulis.controllers

import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo
import static spock.util.matcher.HamcrestSupport.that
import grails.test.mixin.*
import moonillusions.sulis.commands.AddPlayerCommand
import moonillusions.sulis.service.PlayerService

import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse

import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PlayerController)
class PlayerControllerSpec extends Specification {

    PlayerService playerService = Mock(PlayerService)

    AddPlayerCommand command

    def setup() {
        controller.playerService = playerService
        command =  new AddPlayerCommand(name: "new name")
        playerService.create(_) >> { it }
    }

    def cleanup() {
    }

    void "index action maps to player add view"() {

        when:
        controller.index()

        then:
        model.isEmpty()
        view == "/player/add"
    }

    void "add action adds new player"() {

        when:
        controller.add(command)

        then:
        1 * playerService.create({ player ->
            assert player.name == "new name"
            true
        })
    }

    void "add action uses command as model on failure"() {
        when:
        AddPlayerCommand command = new AddPlayerCommand()
        def model = controller.add(command)

        then:
        playerService.create(_) >> { null }
        model == command
    }

    void "add action redirects to game page on success"() {

        when:
        controller.add(command)

        then:
        view == "/game/"
        model.isEmpty()
    }
}
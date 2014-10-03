package moonillusions.sulis.service

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that
import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import moonillusions.sulis.domain.Player
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PlayerService)
@grails.test.mixin.Mock(Player)
@Build(Player)
class PlayerServiceSpec extends spock.lang.Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "list should return empty list if no players"() {
        when:
        def players = service.list()

        then:
        players.size() == 0
    }

    void "list should return all players"() {
        setup:
        def player1 = Player.build().save()
        def player2 = Player.build().save()

        when:
        def players = service.list()

        then:
        players containsInAnyOrder(player2, player1)
    }

    void "get player by name"() {

        setup:
        def player1 = Player.build(name: "Some one").save()

        when:
        def player = service.get("Some one")

        then:
        player.name == "Some one"
    }

    void "create player"()  {

        when:
        def player = service.create("some new name")

        then:
        player.name == "some new name"
        Player.findByName("some new name") != null
    }
}

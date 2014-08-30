package moonillusions.sulis.domain.unit


import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors
import static moonillusions.grails.testing.matchers.FieldErrors.noFieldErrors
import static org.junit.Assert.assertThat
import grails.test.mixin.*
import grails.validation.ValidationException
import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player

import org.joda.time.LocalDate
import org.junit.*


/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Game)
@Mock([Game,Player])
class GameTests {

    Game game
    Player player1
    Player player2

    @Before
    void setup() {
        player1 = new Player(name: "Jack")
        player2 = new Player(name: "Jane")
        game = new Game(servingPlayer: player1, receivingPlayer: player2, servingPlayerPoints: 10, receivingPlayerPoints: 21, date: new LocalDate(2013,1,20));
    }

    @Test
    void happyCase() {
        assert(game.save(failOnError: true))
        assertThat(game, noFieldErrors())
    }

    @Test
    void notNullConstraints() {
        Game game = new Game()

        shouldFail(ValidationException) {
            game.save(failOnError: true)
        }

        assertThat(game, fieldErrors(
                [
                    servingPlayer: "nullable",
                    receivingPlayer: "nullable",
                    date: "nullable",
                    servingPlayerPoints: "nullable",
                    receivingPlayerPoints: "nullable"]));
    }

    @Test
    void pointsNotNegative() {
        game.servingPlayerPoints = -1
        game.receivingPlayerPoints = -1
        shouldFail(ValidationException) {
            game.save(failOnError: true)
        }
        assertThat(game, fieldErrors(servingPlayerPoints: "min.notmet", receivingPlayerPoints: "min.notmet"));
    }
}

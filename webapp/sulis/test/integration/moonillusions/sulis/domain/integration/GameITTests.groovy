package moonillusions.sulis.domain.integration

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import grails.validation.ValidationException
import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player

import org.joda.time.LocalDate
import org.junit.*

class GameITTests extends GroovyTestCase {

    Game game
    Player player1
    Player player2

    @Before
    void setUp() {
        player1 = new Player(name: "Jack")
        player2 = new Player(name: "Jane")
        game = new Game(servingPlayer: player1, receivingPlayer: player2, servingPlayerPoints: 12, receivingPlayerPoints: 21, date: new LocalDate(2013,1,20));
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSaveCascadesToPlayers() {
        assert(game.save(failOnError: true))
        assertThat(player1.id, notNullValue())
        assertThat(player2.id, notNullValue())
    }

    @Test
    void testException_OnCascadeErrors(){
        assert(game.save(failOnError: true, flush: true))
        player2.name = player1.name; // To violate the unique constraint
        game.servingPlayerPoints = game.servingPlayerPoints+1; // To cause update
        shouldFail(ValidationException) {
            game.save(failOnError: true, flush: true)
        }
    }
}

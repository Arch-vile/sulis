package moonillusions.sulis.domain.integration

import static org.junit.Assert.*
import grails.validation.ValidationException;
import groovy.util.GroovyTestCase;
import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player;
import static org.hamcrest.Matchers.*;

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
		game = new Game(player1: player1, player2: player2, points1: 12, points2: 21, date: new LocalDate(2013,1,20));
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void saveCascadesToPlayers() {
        assert(game.save())
		assertThat(player1.id, notNullValue())
		assertThat(player2.id, notNullValue())
    }
	
	@Test
	void exception_OnCascadeErrors(){
		assert(game.save())
		player2.name = player1.name; // To violate the unique constraint
		game.points1 = game.points1+1; // To cause update
		shouldFail(ValidationException) {
			game.save(flush: true)
		}
	}
	
}

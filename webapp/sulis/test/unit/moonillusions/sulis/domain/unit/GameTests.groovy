package moonillusions.sulis.domain.unit



import grails.test.mixin.*
import groovy.util.GroovyTestCase;
import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player

import org.junit.*

import static org.junit.Assert.assertThat

import org.joda.time.LocalDate

import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors;
import static moonillusions.grails.testing.matchers.FieldErrors.noFieldErrors;

import grails.validation.ValidationException


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
		game = new Game(player1: player1, player2: player2, servPoints: 10, points2: 21, date: new LocalDate(2013,1,20));
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
			   player1: "nullable",
			   player2: "nullable",
			   date: "nullable",
			   servPoints: "nullable",
			   points2: "nullable"]));
    }
	
	@Test
	void pointsNotNegative() {
		game.servPoints = -1
		game.points2 = -1
		shouldFail(ValidationException) {
		   game.save(failOnError: true)
		}
		assertThat(game, fieldErrors(servPoints: "min.notmet", points2: "min.notmet"));
	}
}

package com.rapsuveer.sulis.domain.unit



import grails.test.mixin.*

import org.junit.*
import org.joda.time.LocalDate

import com.rapsuveer.sulis.domain.Game;
import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors;


/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Game)
@Mock(Game)
class GameTests {

	Game game
	
	@Before
	void setup() {
		game = new Game(player1: "Juuso", player2: "Heikki", points1: 10, points2: 21, date: new LocalDate(2013,1,20));
	}
	
	
	@Test
    void notNullConstraints() {
       Game game = new Game()
	   assert(!game.save())
	   assertThat(game, fieldErrors(
		   [
			   player1: "nullable",
			   player2: "nullable",
			   date: "nullable",
			   points1: "nullable",
			   points2: "nullable"]));
    }
	
	@Test
	void notBlankConstraints() {
		game.player1 = ""
		game.player2 = ""
		assert(!game.save())
		assertThat(game, fieldErrors(player1: "blank", player2: "blank"));
	}
	
	@Test
	void pointsNotNegative() {
		game.points1 = -1
		game.points2 = -1
		assert(!game.save())
		assertThat(game, fieldErrors(points1: "min.notmet", points2: "min.notmet"));
	}
}

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
		Game game = new Game(player1: "", player2: "", points1: 10, points2: 21, date: new LocalDate(2013,1,20));
		assert(!game.save())
		assertThat(game, fieldErrors(player1: "blank", player2: "blank"));
	}
}

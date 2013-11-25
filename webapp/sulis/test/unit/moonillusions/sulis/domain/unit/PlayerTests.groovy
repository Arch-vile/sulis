
package moonillusions.sulis.domain.unit

import grails.test.mixin.*
import liquibase.exception.SetupException;
import moonillusions.grails.testing.matchers.FieldErrors;
import moonillusions.sulis.domain.Player;

import org.junit.*

import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors;
import static moonillusions.grails.testing.matchers.FieldErrors.noFieldErrors;

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
@Mock(Player)
class PlayerTests {

	Player player
	
	@Before
	void setup() {
		player = new Player(name: "Kalle")
	}
	
	@Test
	void happyCase() {
		assert(player.save())
		assertThat(player, noFieldErrors())
	}
	
	@Test
	void nullConstraints() {
		player.name = null
		assert(!player.save())
		assertThat(player, fieldErrors(name: "nullable"))
	}
	
	@Test
	void sizeMinConstraints() {
		player.name = "a"
		assert(!player.save())
		assertThat(player, fieldErrors(name: "size.toosmall"))
		player.name = "ab"
		assert(player.save())
		assertThat(player, noFieldErrors())
	}
	
	@Test
	void sizeMaxConstraints() {
		player.name = "1234567890123456789012345678901"
		assert(!player.save())
		assertThat(player, fieldErrors(name: "size.toobig"))
		player.name = "123456789012345678901234567890"
		assert(player.save())
		assertThat(player, noFieldErrors())
	}
   
}

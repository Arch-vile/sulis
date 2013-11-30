
package moonillusions.sulis.domain.unit

import grails.test.mixin.*
import liquibase.exception.SetupException;
import moonillusions.grails.testing.matchers.FieldErrors;
import moonillusions.sulis.domain.Player;

import static org.junit.Assert.assertThat
import org.junit.*

import grails.validation.ValidationException

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
		shouldFail(ValidationException) {
			player.save()
		}
		assertThat(player, fieldErrors(name: "nullable"))
	}
	
	@Test
	void sizeMinConstraints() {
		player.name = "a"
		shouldFail(ValidationException) {
			player.save()
		}
		assertThat(player, fieldErrors(name: "size.toosmall"))
		player.name = "ab"
		assert(player.save())
		assertThat(player, noFieldErrors())
	}
	
	@Test
	void sizeMaxConstraints() {
		player.name = "1234567890123456789012345678901"
		shouldFail(ValidationException) {
			player.save()
		}
		assertThat(player, fieldErrors(name: "size.toobig"))
		player.name = "123456789012345678901234567890"
		assert(player.save())
		assertThat(player, noFieldErrors())
	}
   
	@Test
	void nameUniqueContraint() {
		Player player2 = new Player(name: player.name)
		assert(player.save())
		shouldFail(ValidationException) {
			player2.save()
		}
		assertThat(player2, fieldErrors(name: "unique"))
	}
}



package moonillusions.sulis.domain.unit

import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors
import static moonillusions.grails.testing.matchers.FieldErrors.noFieldErrors
import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat
import grails.test.mixin.*
import grails.validation.ValidationException
import moonillusions.sulis.domain.Player

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Player)
class PlayerTests {

    Player player

    @Before
    void setup() {
        player = new Player(name: "Kalle")
    }

    @Test
    void happyCase() {
        assert(player.save(failOnError: true))
        assertThat(player, noFieldErrors())
    }

    @Test
    void nullConstraints() {
        player.name = null
        shouldFail(ValidationException) {
            player.save(failOnError: true)
        }
        assertThat(player, fieldErrors(name: "nullable"))
    }

    @Test
    void sizeMinConstraints() {
        player.name = "a"
        shouldFail(ValidationException) {
            player.save(failOnError: true)
        }
        assertThat(player, fieldErrors(name: "size.toosmall"))
        player.name = "ab"
        assert(player.save(failOnError: true))
        assertThat(player, noFieldErrors())
    }

    @Test
    void sizeMaxConstraints() {
        player.name = "1234567890123456789012345678901"
        shouldFail(ValidationException) {
            player.save(failOnError: true)
        }
        assertThat(player, fieldErrors(name: "size.toobig"))
        player.name = "123456789012345678901234567890"
        assert(player.save(failOnError: true))
        assertThat(player, noFieldErrors())
    }

    @Test
    void nameUniqueContraint() {
        Player player2 = new Player(name: player.name)
        assert(player.save(failOnError: true, flush: true))
        shouldFail(ValidationException) {
            player2.save(failOnError: true, flush: true)
        }
        assertThat(player2, fieldErrors(name: "unique"))
    }
}

package moonillusions.sulis.service



import grails.test.mixin.*
import moonillusions.sulis.domain.Game;
import moonillusions.sulis.domain.Player;

import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GameService)
class GameServiceSpec extends spock.lang.Specification {

	def 'Create should do insert' () {
		setup:
		Game game = Mock()
		
		when:
		service.create(game)
		
		then:
		1 * game.save([insert: true])
	}
	
}

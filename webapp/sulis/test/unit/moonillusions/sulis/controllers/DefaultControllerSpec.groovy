package moonillusions.sulis.controllers

import moonillusions.sulis.domain.Player;

import org.spockframework.compiler.model.ThenBlock;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(DefaultController)
class DefaultControllerSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "Index action should redirect to home page"() {
		when:
		controller.index()
		
		then:
		controller.modelAndView.viewName == "/default/home"
	}
	
	void "Index action should have players a model"() {
		setup:
		Player player1 = new Player()
		Player player2 = new Player()
		
		when:
		controller.index()
		
		then:
		controller.modelAndView.modelMap['players'] == []
	}
	
}
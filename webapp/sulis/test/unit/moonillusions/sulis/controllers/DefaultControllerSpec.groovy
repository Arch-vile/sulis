package moonillusions.sulis.controllers

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
}
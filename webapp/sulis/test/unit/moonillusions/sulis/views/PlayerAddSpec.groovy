
package moonillusions.sulis.views

import moonillusions.sulis.commands.AddPlayerCommand;
import moonillusions.sulis.controllers.PlayerController;
import moonillusions.sulis.testing.HtmlUnitViewSpec;
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import static spock.util.matcher.HamcrestSupport.that
import static com.moonillusions.htmlUnitMatchers.matchers.HasAttribute.hasAttribute
import static com.moonillusions.propertynavigation.PropertyNavigation.of
import static com.moonillusions.propertynavigation.PropertyNavigation.prop


@TestMixin([GroovyPageUnitTestMixin,DomainClassUnitTestMixin])
class PlayerAddSpec extends HtmlUnitViewSpec {

	private static final PLAYER_NAME_INPUT = prop(of(AddPlayerCommand.class).name)
	
	def populatedModel
	
	def setup() {
		
		populatedModel = [
				(PlayerController.MODEL_COMMAND):
					new AddPlayerCommand(name: 'john')
			]
		
	}
	
	def "submits to add action"() {
	
		when:
		def form = renderViewWithModel(xpath: "//form[//input[@type='submit' @value='Add player']]")
		
		then:
		that form, hasAttribute("action","add")
	}
	
	def "input field for player name"() {
		
		when:
		def input = renderViewWithModel(xpath: "//input[@type='text' and @name='${PLAYER_NAME_INPUT}']")
		
		then:
		that input, hasAttribute("value", "")
	}
	
	def "binded values used on render"() {
		
		when: 'Render'
		def output = renderViewWithModel(model: populatedModel)
		
		then: 'Binded values shown'
		that getInput(PLAYER_NAME_INPUT,output), hasAttribute("value","john")
	}
	
}

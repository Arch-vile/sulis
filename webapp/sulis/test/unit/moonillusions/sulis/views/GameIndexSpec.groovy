

package moonillusions.sulis.views

import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOption
import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOptions
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.core.IsNull.nullValue
import static org.hamcrest.Matchers.not
import static spock.util.matcher.HamcrestSupport.that
import grails.test.mixin.TestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import moonillusions.sulis.domain.Player
import moonillusions.sulis.testing.HtmlUnitViewSpec;
import spock.lang.Specification

import com.gargoylesoftware.htmlunit.StringWebResponse
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HTMLParser
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HtmlSelect

@TestMixin(GroovyPageUnitTestMixin)
class GameIndexSpec extends HtmlUnitViewSpec {

	def playersModel
	
	def setup() {
		playersModel = [players: [
			new Player(name: "mikko"),
			new Player(name: "sagi")
		]]
	}
	
	void "servingPlayer selection element lists players"() {

		when: 'Get serving player select element'
		def player1Selection = renderViewWithModel(model: playersModel, xpath: "//select[@name='servingPlayer']")

		then: 'Has expected players as selections'
		that player1Selection, hasOption("mikko", "mikko", 0);
		that player1Selection, hasOption("sagi", "sagi", 1);
	}
	
	void "receiving player selection element lists players"() {

		when: 'Get receiving player select element'
		def selection = renderViewWithModel(model: playersModel, xpath: "//select[@name='receivingPlayer']")

		then: 'Has expected players as selections'
		that selection, hasOption("mikko", "mikko", 0);
		that selection, hasOption("sagi", "sagi", 1);
	}

	void "servingPlayer input field for new player"() {
		
		when: 'Get input for the new serving player'
		def input = renderViewWithModel(xpath: "//input[@name='newServingPlayer']")

		then: 'Exists'
		that input, not(nullValue())
	}
	
	void "receiving player points dropdown"() {
		
		when: 'Get receiving player points selection'
		def selection = renderViewWithModel(xpath: "//select[@name='receivingPlayerScore']")
		
		then: 'Has numbers from 21-0,22,23,24,25'
		that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
	}


}

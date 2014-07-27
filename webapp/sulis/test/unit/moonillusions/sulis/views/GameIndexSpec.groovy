

package moonillusions.sulis.views

import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOption
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

	void "servingPlayer selection element lists players"() {

		setup:
		def myModel = [players: [
				new Player(name: "mikko"),
				new Player(name: "sagi")
			]]

		when: 'Get serving player select element'
		def player1Selection = renderViewWithModel(model: myModel, xpath: "//select[@name='servingPlayer']")

		then: 'Has expected players as selections'
		that player1Selection, hasOption("mikko", "mikko", 0);
		that player1Selection, hasOption("sagi", "sagi", 1);
	}

	void "servingPlayer input field for new player"() {
		
		when: 'Get input for the new serving player'
		def input = renderViewWithModel(xpath: "//input[@name='newServingPlayer']")

		then: 'Exists'
		that input, not(nullValue())
	}


}
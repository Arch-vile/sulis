

package moonillusions.sulis.views

import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOption
import static moonillusions.grails.testing.matchers.ControllerModel.hasModel
import static moonillusions.grails.testing.matchers.ControllerView.renders
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.core.IsNull.nullValue
import static org.hamcrest.Matchers.not
import static spock.util.matcher.HamcrestSupport.that
import grails.test.mixin.TestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import moonillusions.sulis.domain.Player
import spock.lang.Specification

import com.gargoylesoftware.htmlunit.StringWebResponse
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HTMLParser
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HtmlSelect

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestMixin(GroovyPageUnitTestMixin)
class DefaultIndexSpec extends Specification {

	String output

	def setup() {
		output = ""
	}

	def cleanup() {
	}

	void "Home page should say hello"() {

		setup:
		output = renderViewWithModel()

		when:
		String text = asText('//span')[0]

		then:
		that text, equalTo("hello!")
	}

	void "startingPlayer selection lists players"() {

		setup:
		output = renderViewWithModel([players: [
				new Player(name: "mikko"),
				new Player(name: "sagi")
			]])

		when:
		HtmlSelect player1Selection = getElement("//select[@name='startingPlayer']")

		then:
		that player1Selection, hasOption("mikko", "mikko", 0);
		that player1Selection, hasOption("sagi", "sagi", 1);
	}

	void "input field for new starting player"() {
		setup:
		output = renderViewWithModel()
		
		when:
		HtmlInput input = getElement("//input[@name='newStartingPlayer']")

		then:
		that input, not(nullValue())
	}


	private getElement(xpath) {
		StringWebResponse resp = new StringWebResponse(output, new URL("http://localhost"))
		WebClient client = new WebClient()
		HtmlPage page = HTMLParser.parseHtml(resp, client.getCurrentWindow())
		page.getFirstByXPath(xpath)
	}

	private asText(xpath) {
		getElement(xpath).collect { it.asText() }
	}

	private renderViewWithModel(myModel = [:]) {
		render(view: '/default/home', model: myModel)
	}
}
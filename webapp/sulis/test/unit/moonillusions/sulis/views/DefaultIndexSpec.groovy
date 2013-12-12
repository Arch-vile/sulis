
package moonillusions.sulis.views

import moonillusions.sulis.controllers.DefaultController;
import moonillusions.sulis.domain.Player;
import moonillusions.sulis.service.PlayerService;
import static org.hamcrest.Matchers.containsInAnyOrder

import org.spockframework.compiler.model.ThenBlock;

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin;
import grails.test.mixin.web.GroovyPageUnitTestMixin;
import spock.lang.Specification
import static moonillusions.grails.testing.matchers.ControllerView.renders
import static moonillusions.grails.testing.matchers.ControllerModel.hasModel
import static org.hamcrest.Matchers.equalTo
import static spock.util.matcher.HamcrestSupport.that

import com.gargoylesoftware.htmlunit.StringWebResponse
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HTMLParser
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSpan

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestMixin(GroovyPageUnitTestMixin)
class DefaultIndexSpec extends Specification {

	
	def setup() {
	}

	def cleanup() {
	}

	void "Home page should say hello"() {
		
		when:
		String text = asText('//span')[0]
		
		then:
		that text, equalTo("hello!")
		
	}
	
	private getElement(xpath) {
		String output = renderViewWithModel()
		StringWebResponse resp = new StringWebResponse(output, new URL("http://localhost"))
		WebClient client = new WebClient()
		HtmlPage page = HTMLParser.parseHtml(resp, client.getCurrentWindow())
		page.getByXPath(xpath)
	}
	
	private asText(xpath) {
		getElement(xpath).collect { it.asText() }
	}
	
	private renderViewWithModel(myModel = [:]) {
		render(view: '/default/home', model: myModel)
	}
	
}
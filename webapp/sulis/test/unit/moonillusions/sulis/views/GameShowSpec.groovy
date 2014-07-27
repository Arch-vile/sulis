package moonillusions.sulis.views

import static spock.util.matcher.HamcrestSupport.that
import moonillusions.sulis.testing.HtmlUnitViewSpec;
import grails.test.mixin.web.GroovyPageUnitTestMixin
import grails.test.mixin.TestMixin

import static com.moonillusions.htmlUnitMatchers.matchers.AsText.asText

@TestMixin(GroovyPageUnitTestMixin)
class GameShowSpec extends HtmlUnitViewSpec {

	void "Displays the message"() {
		
		when: 'Rendered with message'
		def message = renderViewWithModel(model: [message: "my.message.code"], xpath: '//span[@id="message"]')
		
		then: 'Message is displayed'
		that message, asText("my.message.code")
		
	}
	
	
}

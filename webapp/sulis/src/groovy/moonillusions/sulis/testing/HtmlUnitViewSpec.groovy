package moonillusions.sulis.testing

import spock.lang.Specification

import com.gargoylesoftware.htmlunit.StringWebResponse
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HTMLParser
import com.gargoylesoftware.htmlunit.html.HtmlPage

class HtmlUnitViewSpec extends Specification {

    def getElement(xpath, html) {
        assert xpath, "xpath must not be empty"
        assert html, "html must not be empty"

        StringWebResponse resp = new StringWebResponse(html, new URL("http://localhost"))
        WebClient client = new WebClient()
        HtmlPage page = HTMLParser.parseHtml(resp, client.getCurrentWindow())
        page.getFirstByXPath(xpath)
    }


    def getInput(name, html) {
        getElement("//input[@name='$name']", html)
    }

    def getSelect(name, html) {
        getElement("//select[@name='$name']", html)
    }

    def renderViewWithModel(arguments = [:]) {
        def view = arguments.view ?: defaultName()
        assert view, "No view defined. Introduce 'view' class variable or give as method argument"

        def output = render(view: view, model: arguments.model)
        assert output, "Rendering view '${view}' produced no output"

        if(arguments.xpath) {
            return getElement(arguments.xpath, output)
        }
        return output
    }


    def defaultName() {
        def className = this.class.getCanonicalName()
        if(className.endsWith("Spec")) {
            def namePart = className.substring(0, className.lastIndexOf("Spec"))
            return '/' + namePart.findAll( /([A-Z]+[a-z]*)/ ).join('/').toLowerCase()
        }
    }
}

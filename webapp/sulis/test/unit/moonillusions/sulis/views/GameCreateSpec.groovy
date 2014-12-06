package moonillusions.sulis.views

import static com.moonillusions.htmlUnitMatchers.matchers.AsText.asText
import static com.moonillusions.htmlUnitMatchers.matchers.HasAttribute.hasAttribute
import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOption
import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOptions
import static com.moonillusions.propertynavigation.PropertyNavigation.of
import static com.moonillusions.propertynavigation.PropertyNavigation.prop
import static com.moonillusions.propertynavigation.PropertyNavigation.to
import static org.hamcrest.Matchers.containsInAnyOrder
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.not
import static org.hamcrest.core.IsNull.nullValue
import static spock.util.matcher.HamcrestSupport.that
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import moonillusions.sulis.commands.CreateGameCommand
import moonillusions.sulis.controllers.GameController;
import moonillusions.sulis.domain.Player
import moonillusions.sulis.testing.HtmlUnitViewSpec

import org.joda.time.LocalDate

@TestMixin([GroovyPageUnitTestMixin,DomainClassUnitTestMixin])
class GameCreateSpec extends HtmlUnitViewSpec {

    private static final RECEIVINGPLAYER_HTML_SELECT = prop(of(CreateGameCommand.class).receivingPlayerId)
    private static final SERVINGPLAYER_HTML_SELECT =   prop(of(CreateGameCommand.class).servingPlayerId)
    private static final SERVINGPLAYERPOINTS_HTML_SELECT = prop(of(CreateGameCommand.class).servingPlayerPoints)
    private static final RECEIVINGPLAYERPOINTS_HTML_SELECT = prop(of(CreateGameCommand.class).receivingPlayerPoints)
    private static final DATE_HTML_INPUT = prop(of(CreateGameCommand.class).date)

    def populatedModel
    def freshModel
    def server
    def receiver

    def setup() {
        mockDomain(Player)

        server = (new Player(name: "server")).save()
        receiver = (new Player(name: "receiver")).save()

        populatedModel = [
			(GameController.MODEL_COMMAND): 
				new CreateGameCommand(
					date: (new LocalDate(2014,6,25)).toDate(),
					servingPlayerId: server.id,
					receivingPlayerId: receiver.id,
					servingPlayerPoints: 24,
					receivingPlayerPoints: 12),
			(GameController.MODEL_PLAYERS): [
                server,
                receiver
            ]]
		
		
        freshModel = [
			(GameController.MODEL_COMMAND):
				new CreateGameCommand(date: (new LocalDate(2015,6,25)).toDate()),
			(GameController.MODEL_PLAYERS):
				[server,receiver]
		]
    }

    void "Binded values used on render"() {
        when: 'Render'
        def output = renderViewWithModel(model: populatedModel)

        then: 'Binded value shown'
        that getSelect(SERVINGPLAYER_HTML_SELECT,output), hasOption("server",server.id,true,1)
        that getSelect(RECEIVINGPLAYER_HTML_SELECT,output), hasOption("receiver",receiver.id,true,2)
        that getSelect(SERVINGPLAYERPOINTS_HTML_SELECT, output), hasOption("24",24,true,24)
        that getSelect(RECEIVINGPLAYERPOINTS_HTML_SELECT, output), hasOption("12",12,true,9)
        that getInput(DATE_HTML_INPUT, output), hasAttribute("value","25.6.2014")
    }


    void "servingPlayer selection element lists players"() {

        when: 'Get serving player select element'
        def selection = renderViewWithModel(model: freshModel, xpath: "//select[@name='${SERVINGPLAYER_HTML_SELECT}']")

        then: 'Has expected players as selections'
        that selection, hasOption("-- Choose --", "", true, 0)
        that selection, hasOption("server", server.id, false, 1)
        that selection, hasOption("receiver", receiver.id, false, 2)
    }

    void "receiving player selection element lists players"() {

        when: 'Get receiving player select element'
        def selection = renderViewWithModel(model: freshModel, xpath: "//select[@name='${RECEIVINGPLAYER_HTML_SELECT}']")

        then: 'Has expected players as selections'
        that selection, hasOption("-- Choose --", "", true, 0)
        that selection, hasOption("server",server.id, false, 1);
        that selection, hasOption("receiver", receiver.id, false,  2);
    }

    void "serving player points dropdown"() {

        when: 'Get serving player points selection'
        def selection = renderViewWithModel(model: freshModel, xpath: "//select[@name='${SERVINGPLAYERPOINTS_HTML_SELECT}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "receiving player points dropdown"() {

        when: 'Get receiving player points selection'
        def selection = renderViewWithModel(model: freshModel, xpath: "//select[@name='${RECEIVINGPLAYERPOINTS_HTML_SELECT}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "submit to create action on default controller" () {

        when: 'Get the form'
        def form = renderViewWithModel(model: populatedModel, xpath: "//form[//input[@type='submit' and @value='create']]")

        then: 'action mapped to the create action on default controller'
        that form, hasAttribute("action","/test/create")
    }
}

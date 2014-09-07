



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
import moonillusions.sulis.controllers.CreateGameCommand
import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player
import moonillusions.sulis.testing.HtmlUnitViewSpec

import org.joda.time.LocalDate

@TestMixin([GroovyPageUnitTestMixin,DomainClassUnitTestMixin])
class GameCreateSpec extends HtmlUnitViewSpec {

    private static final GAME_RECEIVINGPLAYER_SELECT = prop(of(CreateGameCommand.class).game.receivingPlayer)
    private static final GAME_SERVINGPLAYER_SELECT =   prop(of(CreateGameCommand.class).game.servingPlayer)
    private static final NEWSERVINGPLAYER_FIELD = prop(of(CreateGameCommand.class).newServingPlayer)
    private static final NEWRECEIVINGPLAYER_FIELD = prop(of(CreateGameCommand.class).newReceivingPlayer)
    private static final GAME_SERVINGPLAYERPOINTS_SELECT = prop(of(CreateGameCommand.class).game.servingPlayerPoints)
    private static final GAME_RECEIVINGPLAYERPOINTS_SELECT = prop(of(CreateGameCommand.class).game.receivingPlayerPoints)
    private static final GAME_DATE = prop(of(CreateGameCommand.class).game.date)

    def modelWithInsertedValues
    def modelWithTemplateValues
    def server
    def receiver

    def setup() {
        mockDomain(Player)

        server = (new Player(name: "server")).save()
        receiver = (new Player(name: "receiver")).save()

        modelWithInsertedValues = [
            players: [
                server,
                receiver
            ],
            createGameCommand: new CreateGameCommand(
            game: new Game(
            date: new LocalDate(2014,6,25),
            servingPlayer: server,
            receivingPlayer: receiver,
            servingPlayerPoints: 24,
            receivingPlayerPoints: 12),
            newServingPlayer: "John",
            newReceivingPlayer: "Mary"
            )
        ];

        modelWithTemplateValues = [
            players: [
                server,
                receiver
            ],
            createGameCommand: new CreateGameCommand(
            game: new Game(
            date: new LocalDate(2015,6,25)))
        ];
    }

    void "Command binded values used on render"() {
        when: 'Render'
        def output = renderViewWithModel(model: modelWithInsertedValues)

        then: 'Binded value shown'
        that getInput(NEWSERVINGPLAYER_FIELD,output), hasAttribute("value","John")
        that getInput(NEWRECEIVINGPLAYER_FIELD,output), hasAttribute("value","Mary")
        that getSelect(GAME_SERVINGPLAYER_SELECT,output), hasOption("server",server.id,true,1)
        that getSelect(GAME_RECEIVINGPLAYER_SELECT,output), hasOption("receiver",receiver.id,true,2)
        that getSelect(GAME_SERVINGPLAYERPOINTS_SELECT, output), hasOption("24",24,true,24)
        that getSelect(GAME_RECEIVINGPLAYERPOINTS_SELECT, output), hasOption("12",12,true,9)
        that getInput(GAME_DATE, output), hasAttribute("value","25.6.2014")
    }


    void "servingPlayer selection element lists players"() {

        when: 'Get serving player select element'
        println GAME_SERVINGPLAYER_SELECT
        def selection = renderViewWithModel(model: modelWithTemplateValues, xpath: "//select[@name='${GAME_SERVINGPLAYER_SELECT}']")

        then: 'Has expected players as selections'
        that selection, hasOption("-- Choose --", "", true, 0)
        that selection, hasOption("server", server.id, false, 1)
        that selection, hasOption("receiver", receiver.id, false, 2)
    }

    void "receiving player selection element lists players"() {

        when: 'Get receiving player select element'
        def selection = renderViewWithModel(model: modelWithTemplateValues, xpath: "//select[@name='${GAME_RECEIVINGPLAYER_SELECT}']")

        then: 'Has expected players as selections'
        that selection, hasOption("-- Choose --", "", true, 0)
        that selection, hasOption("server",server.id, false, 1);
        that selection, hasOption("receiver", receiver.id, false,  2);
    }

    void "servingPlayer input field for new player"() {

        when: 'Get input for the new serving player'
        def input = renderViewWithModel(model: modelWithTemplateValues, xpath: "//input[@name='${NEWSERVINGPLAYER_FIELD}']")

        then: 'Has no default value'
        that input, hasAttribute("value","")
    }

    void "receivingPlayer input field for new player"() {

        when: 'Get input for the new receiving player'
        def input = renderViewWithModel(model: modelWithTemplateValues, xpath: "//input[@name='${NEWRECEIVINGPLAYER_FIELD}']")

        then: 'Has no default value'
        that input, hasAttribute("value","")
    }

    void "serving player points dropdown"() {

        when: 'Get serving player points selection'
        def selection = renderViewWithModel(model: modelWithTemplateValues, xpath: "//select[@name='${GAME_SERVINGPLAYERPOINTS_SELECT}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "receiving player points dropdown"() {

        when: 'Get receiving player points selection'
        def selection = renderViewWithModel(model: modelWithTemplateValues, xpath: "//select[@name='${GAME_RECEIVINGPLAYERPOINTS_SELECT}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "submit to create action on default controller" () {

        when: 'Get the form'
        def form = renderViewWithModel(model: modelWithInsertedValues, xpath: "//form[//input[@type='submit' and @value='create']]")

        then: 'action mapped to the create action on default controller'
        that form, hasAttribute("action","/test/create")
    }
}

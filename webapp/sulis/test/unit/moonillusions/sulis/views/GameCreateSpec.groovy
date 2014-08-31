



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
import grails.test.mixin.web.GroovyPageUnitTestMixin
import moonillusions.sulis.controllers.CreateGameCommand
import moonillusions.sulis.domain.Game
import moonillusions.sulis.domain.Player
import moonillusions.sulis.testing.HtmlUnitViewSpec

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

@TestMixin(GroovyPageUnitTestMixin)
class GameCreateSpec extends HtmlUnitViewSpec {

    private static final GAME_RECEIVINGPLAYER_FIELD = prop(of(CreateGameCommand.class).game.receivingPlayer)
    private static final GAME_SERVINGPLAYER_FIELD =   prop(of(CreateGameCommand.class).game.servingPlayer)
    private static final NEWSERVINGPLAYER_FIELD = prop(of(CreateGameCommand.class).newServingPlayer)
    private static final NEWRECEIVINGPLAYER_FIELD = prop(of(CreateGameCommand.class).newReceivingPlayer)
    private static final GAME_SERVINGPLAYERPOINTS_FIELD = prop(of(CreateGameCommand.class).game.servingPlayerPoints)
    private static final GAME_RECEIVINGPLAYERPOINTS_FIELD = prop(of(CreateGameCommand.class).game.receivingPlayerPoints)
    private static final GAME_DATE = prop(of(CreateGameCommand.class).game.date)

    def model

    def setup() {
        model = [
            players: [
                new Player(name: "mikko"),
                new Player(name: "sagi")
            ],
            createGameCommand: new CreateGameCommand(game: new Game(date: LocalDate.now()))
        ];
    }

    void "bind values are shown"() {
        //TODO: tests for all fields

        setup: 'Binded values'
        model.createGameCommand.newServingPlayer = "John"

        when: 'Render'
        def output = renderViewWithModel(model: model)

        then: 'Binded value shown'
        that getInput(NEWSERVINGPLAYER_FIELD,output), hasAttribute("value","John")
    }


    void "servingPlayer selection element lists players"() {

        when: 'Get serving player select element'
        println GAME_SERVINGPLAYER_FIELD
        def servingPlayerSelection = renderViewWithModel(model: model, xpath: "//select[@name='${GAME_SERVINGPLAYER_FIELD}']")

        then: 'Has expected players as selections'
        that servingPlayerSelection, hasOption("mikko", "mikko", 0)
        that servingPlayerSelection, hasOption("sagi", "sagi", 1)
    }

    void "receiving player selection element lists players"() {

        when: 'Get receiving player select element'
        def selection = renderViewWithModel(model: model, xpath: "//select[@name='${GAME_RECEIVINGPLAYER_FIELD}']")

        then: 'Has expected players as selections'
        that selection, hasOption("mikko", "mikko", 0);
        that selection, hasOption("sagi", "sagi", 1);
    }

    void "servingPlayer input field for new player"() {

        when: 'Get input for the new serving player'
        def input = renderViewWithModel(model: model, xpath: "//input[@name='${NEWSERVINGPLAYER_FIELD}']")

        then: 'Exists'
        that input, not(nullValue())
    }

    void "receivingPlayer input field for new player"() {

        when: 'Get input for the new receiving player'
        def input = renderViewWithModel(model: model, xpath: "//input[@name='${NEWRECEIVINGPLAYER_FIELD}']")

        then: 'Exists'
        that input, not(nullValue())
    }

    void "serving player points dropdown"() {

        when: 'Get serving player points selection'
        def selection = renderViewWithModel(model: model, xpath: "//select[@name='${GAME_SERVINGPLAYERPOINTS_FIELD}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "receiving player points dropdown"() {

        when: 'Get receiving player points selection'
        def selection = renderViewWithModel(model: model, xpath: "//select[@name='${GAME_RECEIVINGPLAYERPOINTS_FIELD}']")

        then: 'Has numbers from 21-0,22,23,24,25'
        that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
    }

    void "game date field"() {

        setup:
        DateTimeFormatter fmt = DateTimeFormat.forPattern("d.M.yyyy");

        when: 'Get the game date field'
        def date = renderViewWithModel(model: model, xpath: "//input[@name='${GAME_DATE}']")

        then: 'Has current date as default'
        println LocalDate.now().toString(fmt)
        that date, hasAttribute("value",LocalDate.now().toString(fmt))
    }

    void "submit to create action on default controller" () {

        when: 'Get the form'
        def form = renderViewWithModel(model: model, xpath: "//form[//input[@type='submit' and @value='create']]")

        then: 'action mapped to the create action on default controller'
        that form, hasAttribute("action","/test/create")
    }
}

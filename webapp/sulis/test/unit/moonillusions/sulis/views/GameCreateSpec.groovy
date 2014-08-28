


package moonillusions.sulis.views

import static com.moonillusions.htmlUnitMatchers.matchers.AsText.asText
import static com.moonillusions.htmlUnitMatchers.matchers.HasAttribute.hasAttribute
import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOption
import static com.moonillusions.htmlUnitMatchers.matchers.HasOption.hasOptions
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

	def model

	def setup() {
		model = [
			players: [
				new Player(name: "mikko"),
				new Player(name: "sagi")
			],
			command: new CreateGameCommand(game: new Game(date: LocalDate.now()))
		];
	}

	void "servingPlayer selection element lists players"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).game.servingPlayer);

		when: 'Get serving player select element'
		def servingPlayerSelection = renderViewWithModel(model: model, xpath: "//select[@name='${fieldName}']")
		println servingPlayerSelection

		then: 'Has expected players as selections'
		that servingPlayerSelection, hasOption("mikko", "mikko", 0);
		that servingPlayerSelection, hasOption("sagi", "sagi", 1);
	}

	void "receiving player selection element lists players"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).game.receivingPlayer);

		when: 'Get receiving player select element'
		def selection = renderViewWithModel(model: model, xpath: "//select[@name='${fieldName}']")

		then: 'Has expected players as selections'
		that selection, hasOption("mikko", "mikko", 0);
		that selection, hasOption("sagi", "sagi", 1);
	}

	void "servingPlayer input field for new player"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).newServingPlayer);

		when: 'Get input for the new serving player'
		def input = renderViewWithModel(model: model, xpath: "//input[@name='${fieldName}']")

		then: 'Exists'
		that input, not(nullValue())
	}

	void "receivingPlayer input field for new player"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).newReceivingPlayer);

		when: 'Get input for the new receiving player'
		def input = renderViewWithModel(model: model, xpath: "//input[@name='${fieldName}']")

		then: 'Exists'
		that input, not(nullValue())
	}

	void "serving player points dropdown"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).game.servingPlayerPoints);

		when: 'Get serving player points selection'
		def selection = renderViewWithModel(model: model, xpath: "//select[@name='${fieldName}']")

		then: 'Has numbers from 21-0,22,23,24,25'
		that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
	}

	void "receiving player points dropdown"() {

		setup:
		def fieldName = prop(to(CreateGameCommand.class).game.receivingPlayerPoints);

		when: 'Get receiving player points selection'
		def selection = renderViewWithModel(model: model, xpath: "//select[@name='${fieldName}']")

		then: 'Has numbers from 21-0,22,23,24,25'
		that selection, hasOptions("21","20","19","18","17","16","15","14","13","12","11","10","9","8","7","6","5","4","3","2","1","0","22","23","24","25")
	}

	void "game date field"() {

		setup:
		DateTimeFormatter fmt = DateTimeFormat.forPattern("d.M.yyyy");
		def fieldName = prop(to(CreateGameCommand.class).game.date);

		when: 'Get the game date field'
		def date = renderViewWithModel(model: model, xpath: "//input[@name='${fieldName}']")

		then: 'Has current date as default'
		println LocalDate.now().toString(fmt)
		that date, hasAttribute("value",LocalDate.now().toString(fmt))
	}

	// TODO:-  submit button - form action

}

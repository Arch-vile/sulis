package moonillusions.sulis.commands

import static moonillusions.grails.testing.matchers.FieldErrors.fieldErrors
import static moonillusions.grails.testing.matchers.FieldErrors.noFieldErrors
import grails.test.mixin.*
import moonillusions.sulis.controllers.GameController
import spock.lang.Specification

@TestFor(GameController)
class AddPlayerCommandSpec extends Specification {

    AddPlayerCommand validCommand

    def setup() {
        validCommand = new AddPlayerCommand(name: "John")
    }

    void "request parameter binding"() {

        setup:
        def params = [:]
        params['name'] = "Jane"

        when:
        AddPlayerCommand command = new AddPlayerCommand()
        controller.bindData(command, params)
        command.validate()

        then:
        !command.hasErrors()
        command.name == "Jane"
    }

    void "minimum size allowed for new player name"() {
        when:
        validCommand.name = "abc"
        validCommand.validate()

        then:
        !command.hasErrors()
        assertThat(validCommand, noFieldErrors())
    }

    void "maximum size allowed for new player name"() {
        when:
        validCommand.name = "12345678901234567890"
        validCommand.validate()

        then:
        !command.hasErrors()
        assertThat(validCommand, noFieldErrors())
    }

    void "minimum size constraints for new player name"() {
        when:
        validCommand.name = "ab"
        validCommand.validate()

        then:
        assertThat(validCommand, fieldErrors(name: "size.toosmall"));
    }

    void "maximum size constraints for new player name"() {
        when:
        validCommand.name = "12345678901234567890b"
        validCommand.validate()

        then:
        assertThat(validCommand, fieldErrors(name: "size.toobig"));
    }

    void "not null constraint for new player name"() {
        when:
        validCommand.name = null
        validCommand.validate()

        then:
        assertThat(validCommand, fieldErrors(name: "nullable"));
    }
}

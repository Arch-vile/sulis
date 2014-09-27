

package moonillusions.sulis.commands

import grails.test.mixin.*
import moonillusions.sulis.controllers.GameController

import org.joda.time.LocalDate

import spock.lang.Specification

@TestFor(GameController)
class CreateGameCommandSpec extends Specification {


    void "request parameter binding"() {

        setup:
        def params = [:]
        params['newServingPlayer']= "John"
        params['newReceivingPlayer'] = "Jane"
        params['date'] = "12.5.2014"
        params['servingPlayerPoints'] = 10
        params['receivingPlayerPoints'] = 21
        params['servingPlayerId'] = 2
        params['receivingPlayerId'] = 100

        when:
        CreateGameCommand command = new CreateGameCommand()
        controller.bindData(command, params)
        command.validate()

        then:
        !command.hasErrors()
        command.newServingPlayer == "John"
        command.newReceivingPlayer == "Jane"
        command.date == new LocalDate(2014,5,12)
        command.servingPlayerPoints == 10
        command.receivingPlayerPoints == 21
        command.servingPlayerId == 2
        command.receivingPlayerId == 100
    }
}

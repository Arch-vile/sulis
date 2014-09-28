package moonillusions.sulis.commands

import grails.validation.Validateable

@Validateable
class AddPlayerCommand {

    String name

    static constraints = { name size: 3..20 }
}

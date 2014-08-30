package moonillusions.sulis.controllers

import grails.validation.Validateable
import moonillusions.sulis.domain.Game

@Validateable
class CreateGameCommand {

    Game game;
    String newServingPlayer;
    String newReceivingPlayer;
}

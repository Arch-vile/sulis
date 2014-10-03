package moonillusions.sulis.service

import moonillusions.sulis.domain.Player

class PlayerService {

    def list() {
        Player.list()
    }

    def get(String name) {
        Player.findByName(name)
    }

    def create(String name) {
        Player player = new Player(name: name)
        player.save()
    }
}

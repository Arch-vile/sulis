package moonillusions.sulis.service

class GameService {

    def create(game) {
		game.save(insert: true)
    }
}

package moonillusions.sulis.domain

import org.joda.time.LocalDate


class Game {

	Player player1
	Player player2
	LocalDate date
	Integer servPoints
	Integer points2
	
	static mapping = {
		player1 cascade: 'save-update'
		player2 cascade: 'save-update'
	}
	
    static constraints = {
		player1 nullable: false
		player2 nullable: false
		date nullable: false
		servPoints nullable: false, min: 0
		points2 nullable: false, min: 0
    }
}

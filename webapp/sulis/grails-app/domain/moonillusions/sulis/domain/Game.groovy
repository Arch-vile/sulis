package moonillusions.sulis.domain

import org.joda.time.LocalDate;

class Game {

	Player player1
	Player player2
	LocalDate date
	Integer points1
	Integer points2
	
	
    static constraints = {
		player1 nullable: false
		player2 nullable: false
		date nullable: false
		points1 nullable: false, min: 0
		points2 nullable: false, min: 0
    }
}

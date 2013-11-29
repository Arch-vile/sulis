package moonillusions.sulis.domain

class Player {

	String name
	
    static constraints = {
		name nullable: false, size: 2..30, unique: true
    }
}

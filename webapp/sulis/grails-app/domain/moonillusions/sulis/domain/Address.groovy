package moonillusions.sulis.domain

class Address {

	String city
	
    static constraints = {
		city nullable: false
    }
}

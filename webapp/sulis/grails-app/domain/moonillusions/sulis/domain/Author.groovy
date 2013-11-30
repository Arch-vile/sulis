package moonillusions.sulis.domain

class Author {

	Address address
	String name
	
	static mapping = {
		address cascade: 'save-update'
	}
	
}

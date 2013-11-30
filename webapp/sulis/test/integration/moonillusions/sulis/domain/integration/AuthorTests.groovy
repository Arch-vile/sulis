package moonillusions.sulis.domain.integration

import static org.junit.Assert.*
import moonillusions.sulis.domain.Address
import moonillusions.sulis.domain.Author

import org.junit.*

import grails.validation.ValidationException


class AuthorTests extends GroovyTestCase {
	
    @Test
    void testSomething() {
		Address address = new Address(city: 'London')
        Author author = new Author(name: "John", address: address)
		author.save()
		author.name = "Jane"
		address.city = null
		shouldFail(ValidationException) {
			author.save(flush: true)
		}
    }
}

import org.junit.Test;


import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static moonillusions.utils.SafeNavigation.prop;

public class SafeNavigationTest {
	
	@Test
	public void public_variable() {
		assertThat(prop(Book.class, "title"), equalTo("title"));
	}
	
	@Test
	public void public_getter() {
		assertThat(prop(Book.class, "pageCount"), equalTo("pageCount"));
	}
	
	@Test
	public void deep_variable() {
		assertThat(prop(Book.class, "author.age"), equalTo("author.age"));
	}
	
	@Test
	public void deep_getter() {
		assertThat(prop(Book.class, "author.name"), equalTo("author.name"));
	}
	
	@Test
	public void class_from_another_package() {
		assertThat(prop(System.class, "out"), equalTo("out"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void fail_simple() {
		prop(Book.class, "titleNot");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void fail_deep() {
		prop(Book.class, "author.noSuchProp");
	}
	
	
	private class Book {
		
		public String title;
		private Integer pageCount;
		public Author author;
		
		public Integer getPageCount() {
			return this.pageCount;
		}
		
	}
	
	private class Author {
		private String name;
		public Integer age;
		
		public String getName() {
			return this.name;
		}
	}

}

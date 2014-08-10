import org.junit.Test;


import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static moonillusions.utils.SafeNavigation.prop;

public class SafeNavigationTest {
	
	@Test
	public void public_variable() {
		assertThat(prop(MyTestClass.class, "name"), equalTo("name"));
	}
	
	@Test
	public void public_getter() {
		assertThat(prop(MyTestClass.class, "age"), equalTo("age"));
	}
	
	@Test
	public void deep_variable() {
		assertThat(prop(MyTestClass.class, "deep.worldPeace"), equalTo("deep.worldPeace"));
	}
	
	@Test
	public void deep_getter() {
		assertThat(prop(MyTestClass.class, "forGetter.type"), equalTo("forGetter.type"));
	}
	
	
	private class MyTestClass {
		
		public String name;
		private Integer age;
		public MyNestedClass deep;
		private MyNestedClass forGetter;
		
		public Integer getAge() {
			return this.age;
		}
		
		public MyNestedClass getForGetter() {
			return this.forGetter;
		}
		
	}
	
	private class MyNestedClass {
		public Boolean worldPeace;
		private String type;
		
		public String getType() {
			return this.type;
		}
	}

}

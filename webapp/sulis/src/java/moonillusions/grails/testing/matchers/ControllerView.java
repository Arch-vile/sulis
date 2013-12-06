package moonillusions.grails.testing.matchers;

import java.util.HashMap;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsControllerClass;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.grails.datastore.mapping.validation.ValidationErrors;
import org.springframework.validation.BeanPropertyBindingResult;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.springframework.validation.FieldError;




import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class ControllerView extends BaseMatcher<Object> {

		private String view;
        
        public ControllerView(String view){
                this.view = view;
        }

        @Override
        public void describeTo(Description description) {
                description.appendText("Expected a controller with view: " + view);
        }
        
        @Override
        public void describeMismatch(final Object item, final Description description){
                description.appendText("view was").appendValue(getView(item));
        }


        @Override
        public boolean matches(Object controller) {
                String view = getView(controller);
                return equalTo(this.view).matches(view);
        }

        private String getView(Object controller) {
        	ModelAndView modelAndView = (ModelAndView)InvokerHelper.invokeMethod(controller, "getModelAndView", null);
        	return modelAndView.getViewName();
        }
        
        @Factory
        public static Matcher<Object> renders(String view) {
         return new ControllerView(view);
        }
        
        
}
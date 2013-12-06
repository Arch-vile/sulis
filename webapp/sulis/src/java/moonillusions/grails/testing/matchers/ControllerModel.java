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


public class ControllerModel extends BaseMatcher<Object> {

		private String key;
		private Matcher<Object> matcher;
        
        public ControllerModel(String key, Matcher<Object> matcher){
                this.key = key;
                this.matcher = matcher;
        }

        @Override
        public void describeTo(Description description) {
                description.appendText("Expected a controller with model: key=" + key + " matching=" + matcher);
        }
        
        @Override
        public void describeMismatch(final Object item, final Description description){
                description.appendText("model was").appendValue(getModel(item));
        }


        @Override
        public boolean matches(Object controller) {
                Object model = getModel(controller);
                return this.matcher.matches(model);
        }

        private Object getModel(Object controller) {
        	ModelAndView modelAndView = (ModelAndView)InvokerHelper.invokeMethod(controller, "getModelAndView", null);
        	return modelAndView.getModel().get(key);
        }
        
        @Factory
        public static Matcher<Object> hasModel(String key, Matcher<Object> matcher) {
        	return new ControllerModel(key, matcher);
        }
        
        
}